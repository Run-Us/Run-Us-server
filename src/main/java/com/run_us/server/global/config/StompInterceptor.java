package com.run_us.server.global.config;

import static com.run_us.server.global.common.GlobalConst.SESSION_ATTRIBUTE_USER;
import static com.run_us.server.global.common.GlobalConst.WS_USER_AUTH_HEADER;

import com.run_us.server.domains.running.controller.model.UserSocketResponseCode;
import com.run_us.server.domains.running.service.SubscriptionService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.service.UserService;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.run_us.server.global.exception.UserSocketException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


/**
 * STOMP 인터셉터로 추후 인증, 권한 체크에 활용할 클래스.
 * Message의 분기를 통해 CONNECT, SUBSCRIBE, SEND, DISCONNECT 등의 이벤트를 처리할 수 있다.
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {
  private final UserService userService;
  private final SubscriptionService subscriptionService;

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {

    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    switch (Objects.requireNonNull(accessor.getCommand())) { // TODO: 인증, 인가, 권한 체크
      case CONNECT -> {
        log.info("CONNECT");
        setUserInfoInSession(accessor);
      }
      case SUBSCRIBE -> {
        log.info("SUBSCRIBE : {} by {}", accessor.getDestination(), accessor.getSubscriptionId());

        // TODO : 중복 로직 - 세션 유저 정보 가져와서 null 이면 예외날리기
        Optional<User> userOp = Optional.ofNullable(accessor.getSessionAttributes())
                .map(attr -> (User) attr.get(SESSION_ATTRIBUTE_USER));
        if(userOp.isEmpty()) {
          throw UserSocketException.of(UserSocketResponseCode.USER_INFO_NOT_EXIST);
        }
        subscriptionService.process(Objects.requireNonNull(accessor.getDestination()), userOp.get());

      }

    }
    return message;
  }

  /**
   * stomp 요청 헤더로부터 user public id 값을 가져와 세션에 User 정보를 저장
   * @param accessor
   */
  private void setUserInfoInSession(StompHeaderAccessor accessor) {
    String userPublicId = accessor.getFirstNativeHeader(WS_USER_AUTH_HEADER);
    User user = userService.getUserByPublicId(userPublicId);
    Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
    sessionAttributes.put(SESSION_ATTRIBUTE_USER, user);
    accessor.setSessionAttributes(sessionAttributes);
//    log.info("CONNECT setUserInfoInSession : {}", accessor.getSessionAttributes().get(SESSION_ATTRIBUTE_USER));
  }
}
