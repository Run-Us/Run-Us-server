package com.run_us.server.global.config;

import com.run_us.server.domains.running.live.controller.aop.UserIdArgumentResolver;
import com.run_us.server.global.exception.StompErrorHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;

import static com.run_us.server.global.common.SocketConst.*;

/**
 * websocket config 클래스
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompInterceptor stompInterceptor;
    private final StompErrorHandler stompErrorHandler;
    private final UserIdArgumentResolver userIdArgumentResolver;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(WS_CONNECT_ENDPOINT)
                .setAllowedOriginPatterns("*"); // ws 연결 요청 - 모든 곳으로부터의 요청 허용

        registry.setErrorHandler(stompErrorHandler);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(WS_DESTINATION_PREFIX_TOPIC, WS_DESTINATION_PREFIX_QUEUE); // 구독, 전송(sub)
        registry.setApplicationDestinationPrefixes(WS_DESTINATION_PREFIX_APP); // 발행(pub)
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompInterceptor);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userIdArgumentResolver);
    }
}
