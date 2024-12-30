package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RunDeleteUseCaseImpl implements RunDeleteUseCase {

  private final UserService userService;
  private final RunCommandService runCommandService;

  @Override
  @Transactional
  public void deleteRun(String userId, String runPublicId) {
    User user = userService.getUserByPublicId(userId);
    runCommandService.deleteRun(user.getId(), runPublicId);
  }
}
