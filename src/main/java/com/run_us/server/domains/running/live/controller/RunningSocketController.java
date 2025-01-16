package com.run_us.server.domains.running.live.controller;


import com.run_us.server.domains.running.live.controller.aop.UserId;
import com.run_us.server.domains.running.live.controller.model.RunningSocketRequest;
import com.run_us.server.domains.running.live.controller.model.RunningSocketResponse;
import com.run_us.server.domains.running.live.controller.model.RunningSocketResponseCode;
import com.run_us.server.domains.running.live.service.RunningLiveService;
import com.run_us.server.domains.running.common.RunningConst;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/** 러닝 websocket 컨트롤러 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class RunningSocketController {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final RunningLiveService runningLiveService;

  /**
   * 러닝 시작
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/runnings/start")
  public void startRunning(@UserId String userId, RunningSocketRequest.StartRunning requestDto) {
    log.info("action=start_running user_id={} running_id={}", userId, requestDto.getRunningPublicId());
    SuccessResponse<RunningSocketResponse.StartRunning> response =
        runningLiveService.startRunning(requestDto.getRunningPublicId(), userId, requestDto.getCount());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningPublicId(), response);
  }

  /**
   * 위치 전송, 라이브 러닝방에 위치정보를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/location")
  public void updateLocation(@UserId String userId,  RunningSocketRequest.LocationUpdate requestDto) {
    log.info("action=update_running user_id={} running_id={}", userId, requestDto.getRunningPublicId());
    SuccessResponse<RunningSocketResponse.LocationUpdate> response = runningLiveService.updateLocation(
        requestDto.getRunningPublicId(),
        userId,
        requestDto.getLatitude(),
        requestDto.getLongitude(),
        requestDto.getCount());
    simpMessagingTemplate.convertAndSend(RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningPublicId(), response);
  }

  /***
   * 러닝 일시정지, 라이브 러닝방에 일시정지 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/pause")
  public void pauseRunning(@UserId String userId, RunningSocketRequest.PauseRunning requestDto) {
    log.info("action=pause_running user_id={} running_id={}", userId, requestDto.getRunningPublicId());
    runningLiveService.pauseRunning(requestDto.getRunningPublicId(), userId, requestDto.getCount());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningPublicId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.PAUSE_RUNNING));
  }

  /***
   * 러닝 재개, 라이브 러닝방에 재개 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/resume")
  public void resumeRunning(@UserId String userId, RunningSocketRequest.ResumeRunning requestDto) {
    log.info("action=resume_running user_id={} running_id={}", userId, requestDto.getRunningPublicId());
    runningLiveService.resumeRunning(requestDto.getRunningPublicId(), userId, requestDto.getCount());
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningPublicId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.RESUME_RUNNING));
  }

  /***
   * 러닝 종료, 라이브 러닝방에 종료 이벤트를 publish
   * @param userId 사용자 (고유번호 세션에서 추출)
   * @param requestDto 요청 body
   */
  @MessageMapping("/users/runnings/end")
  public void endRunning(@UserId String userId,  RunningSocketRequest.StopRunning requestDto) {
    // TODO:check if the user is the owner of the running session
    log.info("action=end_running user_id={} running_id={}", userId, requestDto.getRunningPublicId());
    runningLiveService.endRunning(requestDto.getRunningPublicId(), userId);
    simpMessagingTemplate.convertAndSend(
        RunningConst.RUNNING_WS_SEND_PREFIX + requestDto.getRunningPublicId(),
        SuccessResponse.messageOnly(RunningSocketResponseCode.END_RUNNING));
  }
}
