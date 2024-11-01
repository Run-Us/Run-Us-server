package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.RunningConst;
import com.run_us.server.domains.running.controller.aop.UserId;
import com.run_us.server.domains.running.controller.model.RunningSocketResponseCode;
import com.run_us.server.domains.running.controller.model.request.RunningRequest;
import com.run_us.server.domains.running.controller.model.request.RunningRequest.LocationUpdate;
import com.run_us.server.domains.running.controller.model.response.RunningResponse;
import com.run_us.server.domains.running.service.RunningLiveService;
import com.run_us.server.domains.running.service.RunningResultService;
import com.run_us.server.domains.running.service.model.RunningMapper;
import com.run_us.server.global.common.SuccessResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


import static com.run_us.server.global.common.SocketConst.USER_WS_LOGS_SUBSCRIBE_PATH;

/** 러닝 websocket 컨트롤러 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class RunningSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  private final RunningLiveService runningLiveService;
  private final RunningResultService runningResultService;

  /**
   * 러닝 시작
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/runnings/start")
  public void startRunning(@UserId String userId, RunningRequest.StartRunning requestDto) {
    log.info("action=start_running user_id={} running_id={}", userId, requestDto.getRunningId());
    runningLiveService.startRunning(requestDto.getRunningId(), userId);
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.START_RUNNING));
  }

  /**
   * 위치 전송, 라이브 러닝방에 위치정보를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/location")
  public void updateLocation(@UserId String userId,  LocationUpdate requestDto) {
    log.info("action=update_running user_id={} running_id={}", userId, requestDto.getRunningId());
    runningLiveService.updateLocation(
        requestDto.getRunningId(),
        userId,
        requestDto.getLatitude(),
        requestDto.getLongitude(),
        requestDto.getCount());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.of(
            RunningSocketResponseCode.UPDATE_LOCATION, RunningResponse.LocationData.toDto(requestDto, userId)));
  }

  /***
   * 러닝 일시정지, 라이브 러닝방에 일시정지 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/pause")
  public void pauseRunning(@UserId String userId, RunningRequest.PauseRunning requestDto) {
    log.info("action=pause_running user_id={} running_id={}", userId, requestDto.getRunningId());
    runningLiveService.pauseRunning(requestDto.getRunningId(), userId);
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.PAUSE_RUNNING));
  }

  /***
   * 러닝 재개, 라이브 러닝방에 재개 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/resume")
  public void resumeRunning(@UserId String userId, RunningRequest.ResumeRunning requestDto) {
    log.info("action=resume_running user_id={} running_id={}", userId, requestDto.getRunningId());
    runningLiveService.resumeRunning(requestDto.getRunningId(), userId);
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.RESUME_RUNNING));
  }

  /***
   * 러닝 종료, 라이브 러닝방에 종료 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/end")
  public void endRunning(@UserId String userId,  RunningRequest.StopRunning requestDto) {
    // TODO:check if the user is the owner of the running session
    log.info("action=end_running user_id={} running_id={}", userId, requestDto.getRunningId());
    runningLiveService.endRunning(requestDto.getRunningId(), userId);
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.END_RUNNING));
  }

  private void sendToUser(@NotNull String sessionId, @NotNull String destination, Object payload) {
    SimpMessageHeaderAccessor headerAccessor =
        SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
    headerAccessor.setSessionId(sessionId);
    headerAccessor.setLeaveMutable(true);
    log.info("sendToUser : {}", sessionId);
    simpMessageSendingOperations.convertAndSendToUser(
        sessionId, destination, payload, headerAccessor.getMessageHeaders());
  }
}
