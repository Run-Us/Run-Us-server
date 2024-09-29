package com.run_us.server.domains.user.controller;

import com.run_us.server.domains.user.controller.model.request.UserSignUpRequest;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @PostMapping("/auth/users")
  @Transactional
  public User createUser(@RequestBody UserSignUpRequest userSignUpRequest) {
    // create user
    User user = User.builder()
        .nickname(userSignUpRequest.getNickName())
        .build();
    return userRepository.save(user);
  }
}
