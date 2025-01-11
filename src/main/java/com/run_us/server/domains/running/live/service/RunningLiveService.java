package com.run_us.server.domains.running.live.service;

import static com.run_us.server.domains.running.common.RunningConst.RUNNING_PREFIX;
import static com.run_us.server.domains.running.live.service.util.RunningKeyUtil.createLiveKey;

import com.run_us.server.domains.running.live.controller.model.RunningSocketResponse;
import com.run_us.server.domains.running.live.controller.model.RunningSocketResponseCode;
import com.run_us.server.domains.running.live.repository.RunningRedisRepository;
import com.run_us.server.domains.running.live.repository.UpdateLocationRepository;
import com.run_us.server.domains.running.live.service.model.LocationData;
import com.run_us.server.domains.running.live.service.model.ParticipantStatus;
import com.run_us.server.domains.running.common.RunningConst;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.SuccessResponse;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunningLiveService {

  private final RunningRedisRepository runningRedisRepository;
  private final RunCommandService runCommandService;
  private final RunQueryService runQueryService;
  private final ParticipantService participantService;
  private final UpdateLocationRepository locationRepository;
  private final Map<String, ScheduledExecutorService> sessionSchedulers = new ConcurrentHashMap<>();

  /***
   * 러닝세션 참가: 참가자 상태를 READY로 변경
   * @param runningId 러닝세션 외부 노출용 ID
   * @param user 참가 사용자
   */
  @Transactional
  public void joinLiveRunning(String runningId, User user) {
    Run run = runQueryService.findByRunPublicId(runningId);
    participantService.joinLiveRunning(user.getId(), run);
    runningRedisRepository.updateParticipantStatus(
        runningId, user.getPublicId(), ParticipantStatus.READY);
  }

  /***
   * 러닝 시작: 참가자 상태를 RUN으로 변경
   * @param runningId 러닝세션 외부 노출용 ID
   * @param userId 유저 외부 노출용 ID
   */
  public SuccessResponse<RunningSocketResponse.StartRunning> startRunning(String runningId, String userId, long count) {
    runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.RUN);
    return SuccessResponse.of(
        RunningSocketResponseCode.START_RUNNING,
        new RunningSocketResponse.StartRunning(runningId, count));
  }

  /***
   * 러닝 휴식: 참가자 상태를 PAUSE으로 변경
   * @param runningId 러닝세션 외부 노출용 ID
   * @param userId 유저 외부 노출용 ID
   */
  public SuccessResponse<RunningSocketResponse.PauseRunning> pauseRunning(String runningId, String userId, long count) {
    ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
    if (status != null && status.isRunning()) {
      runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.PAUSE);
    }
    return SuccessResponse.of(
        RunningSocketResponseCode.PAUSE_RUNNING,
        new RunningSocketResponse.PauseRunning(userId, count));
  }

  /***
   * 러닝 재시작: 참가자 상태를 RUN으로 변경
   * @param runningId 러닝세션 외부 노출용 ID
   * @param userId 유저 외부 노출용 ID
   */
  public SuccessResponse<RunningSocketResponse.ResumeRunning> resumeRunning(String runningId, String userId, long count) {
    ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
    if (status != null && status.isPaused()) {
      runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.RUN);
    }
    return SuccessResponse.of(
        RunningSocketResponseCode.RESUME_RUNNING,
        new RunningSocketResponse.ResumeRunning(userId, runningId, count));
  }

  /***
   * 러닝 종료: 참가자 상태를 END로 변경
   * @param runningId 러닝세션 외부 노출용 ID
   * @param userId 유저 외부 노출용 ID
   */
  public List<LocationData.RunnerPos> endRunning(String runningId, String userId) {
    ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
    if (status != null && status.isActive()) {
      runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.END);
      return locationRepository
          .getLocationLogs(String.format("%s:%s", runningId, userId))
          .orElse(null);
    }
    return Collections.emptyList();
  }

  /***
   * 러닝세션 시작: 전체 참가자 상태를 RUN으로 변경하고, 위치 업데이트 스케줄러 시작
   * @param runningId 러닝세션 외부 노출용 ID
   */
  public void startRunningSession(String runningId, long count) {
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(
        () -> runningRedisRepository.publishLocationUpdatesAll(runningId),
        0,
        RunningConst.UPDATE_INTERVAL,
        TimeUnit.MILLISECONDS);
    sessionSchedulers.put(runningId, scheduler);

    Set<String> participants = runningRedisRepository.getSessionParticipants(runningId);
    for (String userId : participants) {
      startRunning(runningId, userId, count);
    }
  }

  /***
   * 러닝세션 종료: 전체 참가자 상태를 END로 변경하고, 위치 업데이트 스케줄러 종료
   * @param runningId 러닝세션 외부 노출용 ID
   */
  public void finishRunningSession(String runningId) {
    ScheduledExecutorService scheduler = sessionSchedulers.remove(runningId);
    if (scheduler != null) {
      scheduler.shutdown();
    }

    Set<String> participants = runningRedisRepository.getSessionParticipants(runningId);
    for (String userId : participants) {
      endRunning(runningId, userId);
    }
  }

  /***
   * 러닝세션 참가자 위치 업데이트: 참가자의 위치를 업데이트하고, 이동 거리가 일정 이상일 경우 즉시 publish
   * @param runningId 러닝세션 외부 노출용 ID
   * @param userId 유저 외부 노출용 ID
   * @param latitude 위도
   * @param longitude 경도
   * @param count 송신 횟수
   */
  public SuccessResponse<RunningSocketResponse.LocationUpdate> updateLocation(
      String runningId, String userId, float latitude, float longitude, long count) {
    runningRedisRepository.updateParticipantLocation(runningId, userId, latitude, longitude, count);

    LocationData.RunnerPos lastLocation =
        runningRedisRepository.getParticipantLocation(runningId, userId);
    LocationData.RunnerPos newLocation = new LocationData.RunnerPos(latitude, longitude);
    locationRepository.saveLocation(createLiveKey(runningId, userId, RUNNING_PREFIX), newLocation);

    if (lastLocation != null && isSignificantMove(lastLocation, newLocation)) {
      runningRedisRepository.publishLocationUpdateSingle(runningId, userId, latitude, longitude);
    }
    return SuccessResponse.of(
        RunningSocketResponseCode.UPDATE_LOCATION,
        new RunningSocketResponse.LocationUpdate(userId, latitude, longitude, count));
  }

  /***
   * 러닝 결과 조회 - 로컬 테스트용 메소드
   * @param runningId 러닝 키
   * @param userId 유저 아이디
   */
  public List<LocationData.RunnerPos> getRunningLogs(String runningId, String userId) {
    return locationRepository
        .getLocationLogs(createLiveKey(runningId, userId, RUNNING_PREFIX))
        .orElse(null);
  }

  /***
   * (내부용) 중요 이벤트인지 검사
   * @param oldLocation 마지막 위치
   * @param newLocation 신규 위치
   * @return 이동 거리가 일정 이상인지 여부
   */
  private boolean isSignificantMove(
      LocationData.RunnerPos oldLocation, LocationData.RunnerPos newLocation) {
    double distance = oldLocation.distanceTo(newLocation);
    return distance >= RunningConst.SIGNIFICANT_DISTANCE;
  }
}
