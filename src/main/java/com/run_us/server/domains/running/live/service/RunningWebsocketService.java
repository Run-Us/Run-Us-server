package com.run_us.server.domains.running.live.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.domains.running.common.RunningConst;
import com.run_us.server.domains.running.live.controller.model.RunningSocketResponse;
import com.run_us.server.domains.running.live.controller.model.RunningSocketResponseCode;
import com.run_us.server.domains.running.live.repository.RunningRedisRepository;
import com.run_us.server.domains.running.live.service.model.LocationData;

import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RunningWebsocketService {

  private final RunningLiveService runningLiveService;
  private final RunningRedisRepository runningRedisRepository;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ObjectMapper objectMapper;
  private final Map<String, ScheduledExecutorService> sessionSchedulers = new ConcurrentHashMap<>();

  /***
   * 러닝세션 시작: 전체 참가자 상태를 RUN으로 변경하고, 위치 업데이트 스케줄러 시작
   * @param runningId 러닝세션 외부 노출용 ID
   */
  public SuccessResponse<RunningSocketResponse.StartRunning> startRunningSession(String runningId, long count) {
    ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(
        new BroadcastPositionsTask(runningId),
        0,
        RunningConst.UPDATE_INTERVAL,
        TimeUnit.MILLISECONDS);
    sessionSchedulers.put(runningId, scheduler);
    return SuccessResponse.of(
        RunningSocketResponseCode.START_RUNNING,
        new RunningSocketResponse.StartRunning(runningId, count));
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
      runningLiveService.endRunning(runningId, userId);
    }
  }

  private class BroadcastPositionsTask implements Runnable {

    private String runningId;

    public BroadcastPositionsTask(String runningId) {
      this.runningId = runningId;
    }

    @Override
    public void run() {
      Map<String, LocationData.RunnerPos> participantsLocations = runningRedisRepository.getAllParticipantsLocations(runningId);
      try {
        String message = objectMapper.writeValueAsString(participantsLocations);
        simpMessagingTemplate.convertAndSend(RunningConst.RUNNING_WS_SEND_PREFIX + runningId, message);
      } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
