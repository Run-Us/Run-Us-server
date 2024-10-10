package com.run_us.server.domains.test;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exception.code.ExampleErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

import static com.run_us.server.global.common.GlobalConst.SESSION_ATTRIBUTE_USER;

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
        log.info("initTest : /topic/test/hello");
        simpMessagingTemplate.convertAndSend("/topic/test/hello", SuccessResponse.messageOnly(ExampleErrorCode.EXAMPLE));
    }

    /**
     * ws session 사용자 정보 저장 테스트
     */
    @MessageMapping("/test/user/session")
    public void userSessionTest(StompHeaderAccessor accessor) {
        log.info("userSessionTest : /topic/test/user/session");
        
        // 세션에 저장된 유저 정보 가져오기
        User user = (User) accessor.getSessionAttributes().get(SESSION_ATTRIBUTE_USER);
        log.info("userSessionTest : user : {}", user);
        
        simpMessagingTemplate.convertAndSend("/topic/test/user/session", SuccessResponse.of(ExampleErrorCode.SUCCESS, user));
    }


    /**
     * SendToUser 테스트 요청 만들기
     */
}
