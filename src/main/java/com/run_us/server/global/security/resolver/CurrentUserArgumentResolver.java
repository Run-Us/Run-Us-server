package com.run_us.server.global.security.resolver;

import com.run_us.server.domains.user.exception.UserErrorCode;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.global.security.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserIdResolver userIdResolver;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String userPublicId = extractUserPublicId();
        return userIdResolver.resolveUser(userPublicId);
    }

    private String extractUserPublicId() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserException(UserErrorCode.JWT_BROKEN);
        }
        return authentication.getName();
    }
}
