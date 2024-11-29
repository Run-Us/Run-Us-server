package com.run_us.server.domains.running.live.controller.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * [중요] : STOMP 에서만 사용할 수 있습니다.
 * 사용자 고유번호를 추출하기 위한 어노테이션
 * STOMP 헤더에서 유저 정보를 추출하기 위해 사용
 * */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserId {}
