package com.run_us.server.domain.test;

import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exceptions.enums.ExampleErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 테스트용 api 컨트롤러
 */
@Slf4j
@Controller
public class TestController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public TestController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * websocket 초기 세팅 테스트용 요청
     */
    @MessageMapping("/test/hello")
    public void initTest() {
        //TODO : 소켓 응답 코드에 대한 논의 필요. 임시로 EXAMPLE 응답
        simpMessagingTemplate.convertAndSend("/hello", SuccessResponse.messageOnly(ExampleErrorCode.EXAMPLE));
        log.info("initTest : /topic/test/hello");
    }
}
