package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.RunningConst;
import com.run_us.server.domains.running.controller.model.enums.RunningSocketResponseCode;
import com.run_us.server.domains.running.controller.model.request.RunningRequest;
import com.run_us.server.domains.running.controller.model.request.RunningRequest.LocationUpdate;
import com.run_us.server.domains.running.controller.model.response.RunningResponse;
import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.domains.running.service.RunningLiveService;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.domains.running.service.RunningResultService;
import com.run_us.server.global.common.ErrorResponse;
import com.run_us.server.global.common.SuccessResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/** 러닝 websocket 컨트롤러 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class RunningSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  private final RunningLiveService runningLiveService;
  private final RunningPreparationService runningPreparationService;
  private final RunningResultService runningResultService;

  /**
   * 러닝 시작
   *
   * @param requestDto 요청 body
   */
  @MessageMapping("/runnings/start")
  public void startRunning(RunningRequest.StartRunning requestDto) {
    runningLiveService.startRunning(requestDto.getRunningKey(), requestDto.getUserId());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningKey(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.START_RUNNING));
  }

  /**
   * 위치 전송, 라이브 러닝방에 위치정보를 publish
   *
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/location")
  public void updateLocation(LocationUpdate requestDto) {
    runningLiveService.updateLocation(
        requestDto.getRunningId(),
        requestDto.getUserId(),
        requestDto.getLatitude(),
        requestDto.getLongitude(),
        requestDto.getCount());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.of(
            RunningSocketResponseCode.UPDATE_LOCATION, RunningResponse.LocationData.toDto(requestDto)));
  }

  /***
   * 러닝 일시정지, 라이브 러닝방에 일시정지 이벤트를 publish
   * @param requestDto
   */
  @MessageMapping("/users/runnings/pause")
  public void pauseRunning(RunningRequest.PauseRunning requestDto) {
    log.info("pauseRunning : {}", requestDto.getRunningId());
    runningLiveService.pauseRunning(requestDto.getRunningId(), requestDto.getUserId());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.PAUSE_RUNNING));
  }

  /***
   * 러닝 재개, 라이브 러닝방에 재개 이벤트를 publish
   * @param requestDto
   */
  @MessageMapping("/users/runnings/resume")
  public void resumeRunning(RunningRequest.ResumeRunning requestDto) {
    log.info("resumeRunning : {}", requestDto.getRunningId());
    runningLiveService.resumeRunning(requestDto.getRunningId(), requestDto.getUserId());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.RESUME_RUNNING));
  }

  /***
   * 러닝 종료, 라이브 러닝방에 종료 이벤트를 publish
   * @param requestDto
   */
  @MessageMapping("/users/runnings/end")
  public void endRunning(RunningRequest.StopRunning requestDto) {
    // TODO:check if the user is the owner of the running session
    log.info("endRunning : {}", requestDto.getRunningId());
    runningLiveService.endRunning(requestDto.getRunningId(), requestDto.getUserId());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.END_RUNNING));
  }

  /***
   * 러닝 결과 집계, 결과를 저장하고 라이브 러닝방에 결과를 publish
   * @param requestDto
   */
  @MessageMapping("/users/runnings/aggregate")
  public void aggregateRunning(
          @Header("simpSessionId") String sessionId, RunningRequest.AggregateRunning requestDto) {
    try {
      runningResultService.saveRunningResult(
              requestDto.getRunningId(), requestDto.getUserId(), requestDto.getDataList());
    } catch (Exception e) {
      sendToUser(sessionId, "/queue/logs", ErrorResponse.of(RunningErrorCode.AGGREGATE_FAILED));
      return;
    }
    sendToUser(
            sessionId, "/queue/logs", SuccessResponse.messageOnly(RunningSocketResponseCode.END_RUNNING));
  }

  /***
   * 사용자의 요청메세지에서 subscribeId를 추출
   * @param message
   * @return
   */
  private String getSubscriptionId(Message<?> message) {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
    return headerAccessor.getSubscriptionId();
  }

  private void sendToUser(@NotNull String sessionId, @NotNull String destination, Object payload) {
    SimpMessageHeaderAccessor headerAccessor =
        SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
    simpMessageSendingOperations.convertAndSendToUser(
        sessionId, destination, payload, headerAccessor.getMessageHeaders());
  }
}
