package com.run_us.server.domain.running.controller;

import com.run_us.server.domain.running.dto.RunningRequest;
import com.run_us.server.domain.running.dto.RunningResponse;
import com.run_us.server.global.common.GlobalConsts;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exceptions.enums.SocketResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 러닝 websocket 컨트롤러
 */
@RequiredArgsConstructor
@Controller
public class RunningSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 러닝 시작
     * @param requestDto 요청 body
     */
    @MessageMapping("/runnings/start")
    public void startRunning(RunningRequest.StartRunning requestDto) {
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningKey(),
                SuccessResponse.messageOnly(SocketResponseCode.START_RUNNING));
    }


    /**
     * 위치 전송
     * @param requestDto 요청 body
     */
    @MessageMapping("/users/runnings/location")
    public void updateLocation(RunningRequest.LocationData requestDto) {
        simpMessagingTemplate.convertAndSend(GlobalConsts.RUNNING_WS_SEND_PREFIX + requestDto.getRunningKey(),
                SuccessResponse.of(SocketResponseCode.UPDATE_LOCATION, RunningResponse.LocationData.toDto(requestDto)));
    }

}
