package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.common.PassCodeRegistry;
import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.domain.RunningPreview;
import com.run_us.server.v2.running.run.service.RunCommandService;
import com.run_us.server.v2.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.v2.running.run.service.model.SessionRunCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RunCreateUseCaseImpl implements RunCreateUseCase {

  private final RunCommandService runCommandService;
  private final UserService userService;
  private final PassCodeRegistry passCodeRegistry;

  // Custom Run은 생성하면서 바로 Passcode를 생성하여 반환한다.
  public CustomRunCreateResponse saveNewCustomRun(String userId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runCommandService.saveNewRun(user, null);
    String passcode = passCodeRegistry.generateAndGetPassCode(run.getPublicId());
    return CustomRunCreateResponse.from(run, passcode);
  }

  @Override
  public SessionRunCreateResponse saveNewSessionRun(String userId, RunningPreview runningPreview) {
    User user = userService.getUserByPublicId(userId);
    return SessionRunCreateResponse.from(runCommandService.saveNewRun(user, runningPreview));
  }
}
