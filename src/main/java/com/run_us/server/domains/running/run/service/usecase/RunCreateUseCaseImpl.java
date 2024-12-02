package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunningPreview;
import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;

import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RunCreateUseCaseImpl implements RunCreateUseCase {

  private final RunCommandService runCommandService;
  private final UserService userService;
  private final PassCodeRegistry passCodeRegistry;

  // Custom Run은 생성하면서 바로 Passcode를 생성하여 반환한다.
  public SuccessResponse<CustomRunCreateResponse> saveNewCustomRun(String userId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runCommandService.saveNewRun(user, null);
    String passcode = passCodeRegistry.generateAndGetPassCode(run.getPublicId());
    return SuccessResponse.of(RunningHttpResponseCode.RUNNING_CREATED, CustomRunCreateResponse.from(run, passcode));
  }

  @Override
  public SuccessResponse<SessionRunCreateResponse> saveNewSessionRun(String userId, RunningPreview runningPreview) {
    User user = userService.getUserByPublicId(userId);
    return SuccessResponse.of(RunningHttpResponseCode.RUNNING_CREATED,SessionRunCreateResponse.from(runCommandService.saveNewRun(user, runningPreview)));
  }
}
