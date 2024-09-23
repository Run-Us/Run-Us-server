package com.run_us.server.domains.running.controller;

import com.run_us.server.domains.running.controller.model.request.RunningRequest;
import com.run_us.server.domains.running.controller.model.request.RunningRequest.LocationUpdate;
import com.run_us.server.domains.running.controller.model.response.RunningResponse;
import com.run_us.server.domains.running.service.RunningLiveService;
import com.run_us.server.domains.running.service.RunningPreparationService;
import com.run_us.server.domains.running.service.RunningResultService;
import com.run_us.server.global.common.GlobalConsts;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exceptions.enums.SocketResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

/**
 * 러닝 websocket 컨트롤러
 */
@Slf4j
@RequiredArgsConstructor
@Controller
public class RunningSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    private final RunningLiveService runningLiveService;
    private final RunningPreparationService runningPreparationService;
    private final RunningResultService runningResultService;

    /***
     * CONNECTED 이후 라이브세션의 정보를 조회하는 요청; 브로커 없이 일회성
     * @param runningKey 러닝 키
     * @return hello
     */
    @SubscribeMapping("/runnings/{runningKey}")
    public String connectLiveRunning(@DestinationVariable String runningKey, Message<?> message) {
        log.info("User Requested running-session : {} info ", runningKey, getSubscriptionId(message));
        runningPreparationService.joinRunning(runningKey, getSubscriptionId(message));
        runningLiveService.joinLiveRunning(runningKey, getSubscriptionId(message));
        return new StringFormattedMessage(
            "User %s is Connected to Running session %s",
            runningKey,
            getSubscriptionId(message)).toString();
    }

    /**
     * 러닝 시작
     * @param requestDto 요청 body
     */
    @MessageMapping("/runnings/start")
    public void startRunning(RunningRequest.StartRunning requestDto) {
        runningLiveService.startRunning(requestDto.getRunningKey(), requestDto.getUserId());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningKey(),
                SuccessResponse.messageOnly(SocketResponseCode.START_RUNNING));
    }

    /**
     * 위치 전송, 라이브 러닝방에 위치정보를 publish
     * @param requestDto 요청 body
     */
    @MessageMapping("/users/runnings/location")
    public void updateLocation(LocationUpdate requestDto) {
        runningLiveService.updateLocation(requestDto.getRunningId(), requestDto.getUserId(), requestDto.getLatitude(), requestDto.getLongitude(),
            requestDto.getCount());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
                SuccessResponse.of(SocketResponseCode.UPDATE_LOCATION, RunningResponse.LocationData.toDto(requestDto)));
    }

    /***
     * 러닝 일시정지, 라이브 러닝방에 일시정지 이벤트를 publish
     * @param requestDto
     */
    @MessageMapping("/users/runnings/pause")
    public void pauseRunning(RunningRequest.PauseRunning requestDto) {
        log.info("pauseRunning : {}", requestDto.getRunningId());
        runningLiveService.pauseRunning(requestDto.getRunningId(), requestDto.getUserId());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
                SuccessResponse.messageOnly(SocketResponseCode.PAUSE_RUNNING));
    }

    /***
     * 러닝 재개, 라이브 러닝방에 재개 이벤트를 publish
     * @param requestDto
     */
    @MessageMapping("/users/runnings/resume")
    public void resumeRunning(RunningRequest.ResumeRunning requestDto) {
        log.info("resumeRunning : {}", requestDto.getRunningId());
        runningLiveService.resumeRunning(requestDto.getRunningId(), requestDto.getUserId());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
                SuccessResponse.messageOnly(SocketResponseCode.RESUME_RUNNING));
    }

    /***
     * 러닝 종료, 라이브 러닝방에 종료 이벤트를 publish
     * @param requestDto
     */
    @MessageMapping("/users/runnings/end")
    public void endRunning(RunningRequest.StopRunning requestDto) {
        //TODO:check if the user is the owner of the running session
        log.info("endRunning : {}", requestDto.getRunningId());
        runningLiveService.endRunning(requestDto.getRunningId(), requestDto.getUserId());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
                SuccessResponse.messageOnly(SocketResponseCode.END_RUNNING));
    }

    /***
     * 러닝 결과 집계, 결과를 저장하고 라이브 러닝방에 결과를 publish
     * @param requestDto
     */
    @MessageMapping("/users/runnings/aggregate")
    public void aggregateRunning(RunningRequest.AggregateRunning requestDto) {
        log.info("aggregateRunning : {}", requestDto.getRunningId());
        runningResultService.saveRunningResult(requestDto.getRunningId(), requestDto.getUserId(), requestDto.getDataList());
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningId(),
                SuccessResponse.of(SocketResponseCode.ARRIVE_RUNNING, requestDto.getDataList()));
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
}
