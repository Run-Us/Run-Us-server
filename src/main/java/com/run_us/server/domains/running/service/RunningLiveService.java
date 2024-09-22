package com.run_us.server.domains.running.service;

import com.run_us.server.domains.running.domain.LocationData;
import com.run_us.server.domains.running.domain.ParticipantStatus;
import com.run_us.server.domains.running.domain.RunningConstants;
import com.run_us.server.domains.running.repository.RunningRedisRepository;
import com.run_us.server.domains.running.repository.RunningRepository;
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
    private final RunningRepository runningRepository;
    private final Map<String, ScheduledExecutorService> sessionSchedulers = new ConcurrentHashMap<>();

    /***
     * 러닝세션 참가: 참가자 상태를 READY로 변경
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     */
    public void joinLiveRun(String runningId, String userId) {
        runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.READY);
    }

    /***
     * 러닝 시작: 참가자 상태를 RUN으로 변경
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     */
    public void startRun(String runningId, String userId) {
        runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.RUN);
    }

    /***
     * 러닝 휴식: 참가자 상태를 PAUSE으로 변경
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     */
    public void pauseRun(String runningId, String userId) {
        ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
        if(status != null && status.isRunning()) {
            runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.PAUSE);
        }
    }

    /***
     * 러닝 재시작: 참가자 상태를 RUN으로 변경
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     */
    public void resumeRun(String runningId, String userId) {
        ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
        if(status != null && status.isPaused()) {
            runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.RUN);
        }
    }

    /***
     * 러닝 종료: 참가자 상태를 END로 변경
     * @param runningId 러닝세션 외부 노출용 ID
     * @param userId 유저 외부 노출용 ID
     */
    public void endRun(String runningId, String userId) {
        ParticipantStatus status = runningRedisRepository.getParticipantStatus(runningId, userId);
        if(status != null && status.isActive()) {
            runningRedisRepository.updateParticipantStatus(runningId, userId, ParticipantStatus.END);
        }
    }

    /***
     * 러닝세션 시작: 전체 참가자 상태를 RUN으로 변경하고, 위치 업데이트 스케줄러 시작
     * @param runningId 러닝세션 외부 노출용 ID
     */
    public void startRunningSession(String runningId) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> runningRedisRepository.publishLocationUpdatesAll(runningId),
                0, RunningConstants.UPDATE_INTERVAL, TimeUnit.MILLISECONDS);
        sessionSchedulers.put(runningId, scheduler);

        Set<String > participants = runningRedisRepository.getSessionParticipants(runningId);
        for (String userId : participants) {
            startRun(runningId, userId);
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
            endRun(runningId, userId);
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
    public void updateLocation(String runningId, String userId, double latitude, double longitude, long count) {
        runningRedisRepository.updateParticipantLocation(runningId, userId, latitude, longitude, count);

        LocationData.Point lastLocation = runningRedisRepository.getParticipantLocation(runningId, userId);
        LocationData.Point newLocation = new LocationData.Point(latitude, longitude);

        if (lastLocation != null && isSignificantMove(lastLocation, newLocation)) {
            runningRedisRepository.publishLocationUpdateSingle(runningId, userId, latitude, longitude);
        }
    }

    /***
     * (내부용) 중요 이벤트인지 검사
     * @param oldLocation 마지막 위치
     * @param newLocation 신규 위치
     * @return 이동 거리가 일정 이상인지 여부
     */
    private boolean isSignificantMove(LocationData.Point oldLocation, LocationData.Point newLocation) {
        double distance = oldLocation.distanceTo(newLocation);
        return distance >= RunningConstants.SIGNIFICANT_DISTANCE;
    }
}