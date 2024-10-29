package com.run_us.server.domains.running.controller.aop;

import com.run_us.server.domains.user.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.run_us.server.global.common.GlobalConst.SESSION_ATTRIBUTE_USER;

@Component
public class UserIdArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(UserId.class) != null;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, Message<?> message) throws Exception {
    SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(message);
    User user = (User) Objects.requireNonNull(headerAccessor.getSessionAttributes()).get(SESSION_ATTRIBUTE_USER);
    return user.getPublicId();
  }
}