package com.run_us.server.domains.test;

import static com.run_us.server.global.common.GlobalConst.SESSION_ATTRIBUTE_USER;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.common.SuccessResponse;
import com.run_us.server.global.exception.BusinessException;
import com.run_us.server.global.exception.code.ExampleErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;

/** 테스트용 api 컨트롤러 */
@Slf4j
@Controller
public class TestController {
  private final SimpMessagingTemplate simpMessagingTemplate;

  public TestController(SimpMessagingTemplate simpMessagingTemplate) {
    this.simpMessagingTemplate = simpMessagingTemplate;
  }

  /** websocket 초기 세팅 테스트용 요청 */
  @MessageMapping("/test/hello")
  public void initTest() {
    // TODO : 소켓 응답 코드에 대한 논의 필요. 임시로 EXAMPLE 응답
    log.info("initTest : /topic/test/hello");
    simpMessagingTemplate.convertAndSend(
        "/topic/test/hello", SuccessResponse.messageOnly(ExampleErrorCode.EXAMPLE));
  }

  /** ws session 사용자 정보 저장 테스트 */
  @MessageMapping("/test/user/session")
  public void userSessionTest(StompHeaderAccessor accessor) {
    log.info("userSessionTest : /topic/test/user/session");

    // 세션에 저장된 유저 정보 가져오기
    User user = (User) accessor.getSessionAttributes().get(SESSION_ATTRIBUTE_USER);
    log.info("userSessionTest : user : {}", user);

    simpMessagingTemplate.convertAndSend(
        "/topic/test/user/session", SuccessResponse.of(ExampleErrorCode.SUCCESS, user));
  }

  /** BusinessException 에러 소켓에서 내는 요청 */
  @MessageMapping("/test/errors/0")
  public void occurBusinessException() {
    log.info("occurBusinessException : /test/errors/0");
    throw BusinessException.of(ExampleErrorCode.EXAMPLE);
  }

  /** 소켓 RunningException 에러 소켓에서 내는 요청 */
  @MessageMapping("/test/errors/1")
  public void occurRunningException() {
    log.info("occurRunningException : /test/errors/1");
    throw RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND);
  }
}
