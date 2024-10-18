package com.run_us.server.global.exception;

import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;

public interface SocketExceptionCustomHandler {
    /**
     * 해당 핸들러가 처리할 수 있는 예외인지 반환
     * @param e : 확인할 예외
     * @return 해당 예외 처리 가능여부
     */
    boolean canHandle(Throwable e);

    /**
     * 예외 처리 메서드
     * @param clientMessage : 클라이언트가 보낸 메세지
     * @param e : 처리할 예외
     * @return 클라이언트에 전달할 응답
     */
    @Nullable
    Message<byte[]> handle(@Nullable Message<byte[]> clientMessage, Throwable e);

}
