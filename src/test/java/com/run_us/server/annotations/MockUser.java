package com.run_us.server.annotations;

import com.run_us.server.config.WithMockCustomUserSecurityContextFactory;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface MockUser {
  int id() default 1;
  String username() default "admin";
  String password() default "admin";
}
