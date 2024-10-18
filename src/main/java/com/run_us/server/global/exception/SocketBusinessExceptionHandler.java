package com.run_us.server.global.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.run_us.server.global.common.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class SocketBusinessExceptionHandler implements SocketExceptionCustomHandler {
    private final ObjectMapper objectMapper;

    @Override
    public boolean canHandle(Throwable e) {
        return e instanceof BusinessException;
    }

    @Override
    public Message<byte[]> handle(Message<byte[]> clientMessage, Throwable e) {
        log.info("[StompErrorHandler] BusinessException");
        return createErrorMessage((BusinessException) e);
    }

    private Message<byte[]> createErrorMessage(BusinessException e) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        String errorBody = "";
        try {
            errorBody = objectMapper.writeValueAsString(ErrorResponse.of(e.getErrorCode()));
        }
        catch (JsonProcessingException jsonProcessingException) {
            log.info("[StompErrorHandler] exception cannot convert to json");
        }
        return MessageBuilder.createMessage(errorBody.getBytes(StandardCharsets.UTF_8), accessor.getMessageHeaders());
    }
}
