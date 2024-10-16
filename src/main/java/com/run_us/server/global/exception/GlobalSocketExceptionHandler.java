package com.run_us.server.global.exception;

import com.run_us.server.global.common.ErrorResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.run_us.server.global.common.SocketConst.USER_WS_ERROR_SUBSCRIBE_PATH;
import static com.run_us.server.global.common.SocketConst.WS_SESSION_ID_HEADER;

/**
 * 소켓 컨트롤러 이후 단에서 발생하는 에러 핸들러
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalSocketExceptionHandler extends ResponseEntityExceptionHandler {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageExceptionHandler(BusinessException.class)
    public void handleBusinessException(BusinessException e, @Header(WS_SESSION_ID_HEADER) String sessionId) {
        log.info("[ExceptionHandler] catch BusinessException");
        sendToUser(sessionId, USER_WS_ERROR_SUBSCRIBE_PATH, ErrorResponse.of(e.getErrorCode()));
    }

    // TODO : 중복 로직
    private void sendToUser(@NotNull String sessionId, @NotNull String destination, Object payload) {
        SimpMessageHeaderAccessor headerAccessor =
                SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        simpMessagingTemplate.convertAndSendToUser(
                sessionId, destination, payload, headerAccessor.getMessageHeaders());
    }

}
