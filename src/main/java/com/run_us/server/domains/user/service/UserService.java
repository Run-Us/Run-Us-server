package com.run_us.server.domains.user.service;

import static com.run_us.server.domains.user.exception.UserErrorCode.USER_NOT_FOUND;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserException;
import com.run_us.server.domains.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;


  /**
   * publicId 로 유저 정보를 가져오는 메서드 - 이후 여러 도메인에서 쓰임이 많아지면 helper 등으로 분리 예정
   *
   * @param publicId 유저 고유번호(public)
   * @return 유저 정보
   */
  public User getUserByPublicId(String publicId) {
    return userRepository
        .findByPublicId(publicId)
        .orElseThrow(() -> UserException.of(USER_NOT_FOUND));
  }

  public Map<Integer, User> getUserMapByIds(List<Integer> userIds) {
    if (userIds == null || userIds.isEmpty()) {
      return Collections.emptyMap();
    }

    List<User> users = userRepository.findAllByIdIn(userIds);
    return users.stream()
            .collect(Collectors.toMap(User::getId, Function.identity()));
  }
}
