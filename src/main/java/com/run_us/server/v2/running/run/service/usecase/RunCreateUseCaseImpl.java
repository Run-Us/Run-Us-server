package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.run.domain.RunningPreview;
import com.run_us.server.v2.running.run.service.RunCommandService;
import com.run_us.server.v2.running.run.service.model.RunCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RunCreateUseCaseImpl implements RunCreateUseCase {

  private final RunCommandService runCommandService;
  private final UserService userService;

  public RunCreateResponse saveNewCustomRun(String userId) {
    User user = userService.getUserByPublicId(userId);
    return RunCreateResponse.from(runCommandService.saveNewRun(user, null));
  }

  @Override
  public RunCreateResponse saveNewSessionRun(String userId, RunningPreview runningPreview) {
    User user = userService.getUserByPublicId(userId);
    return RunCreateResponse.from(runCommandService.saveNewRun(user, runningPreview));
  }
}
