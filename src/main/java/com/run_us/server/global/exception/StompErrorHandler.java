package com.run_us.server.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StompErrorHandler extends StompSubProtocolErrorHandler {
    private final List<SocketExceptionCustomInterceptor> handlers; // 추후 exception 별 별개의 처리가 필요할 경우 대비 - handler 따로따로 구현하면 됨

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage, Throwable ex) {

        Throwable cause = ex.getCause();
        for(SocketExceptionCustomInterceptor handler : handlers) {
            if(handler.canHandle(cause)) {
                return handler.handle(clientMessage, cause);
            }
        }
        return super.handleClientMessageProcessingError(clientMessage, ex);
    }
}
