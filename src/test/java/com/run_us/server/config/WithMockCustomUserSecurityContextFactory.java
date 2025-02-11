package com.run_us.server.config;

import com.run_us.server.annotations.MockUser;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Collections;

public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<MockUser> {
  @Override
  public SecurityContext createSecurityContext(MockUser annotation) {
      SecurityContext context = SecurityContextHolder.createEmptyContext();
      User user = UserFixtures.getDefaultUser();
      Authentication authentication = new UsernamePasswordAuthenticationToken(
          user,
          null,
          Collections.EMPTY_LIST
      );
      context.setAuthentication(authentication);
      return context;
    }
}
