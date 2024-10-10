package com.run_us.server.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class StompErrorHandler extends StompSubProtocolErrorHandler {

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {

        Throwable cause = ex.getCause();
        if(cause instanceof BusinessException) {
            return createErrorMessage((BusinessException) cause); // TODO : 여기서 대신 GlobalExceptionHandler 의 메서드를 호출해서 로직을 모아둘 수도 있음
        }

        return super.handleClientMessageProcessingError(clientMessage, ex);
    }

    private Message<byte[]> createErrorMessage(BusinessException exception) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        String errorMessage = exception.getLogMessage();
        return MessageBuilder.createMessage(errorMessage.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
