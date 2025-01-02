package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.running.run.service.model.CustomRunCreateResponse;
import com.run_us.server.domains.running.run.service.model.RunCreateDto;
import com.run_us.server.domains.running.run.service.model.SessionRunCreateResponse;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RunCreateUseCaseImpl implements RunCreateUseCase {

  private final RunCommandService runCommandService;
  private final ParticipantService participantService;
  private final UserService userService;
  private final PassCodeRegistry passCodeRegistry;

  // Custom Run은 생성하면서 바로 Passcode를 생성하여 반환한다.
  @Override
  @Transactional
  public SuccessResponse<CustomRunCreateResponse> saveNewCustomRun(String userId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runCommandService.saveNewCustomRun(user);
    String passcode = passCodeRegistry.generateAndGetPassCode(run.getPublicId());
    return SuccessResponse.of(RunningHttpResponseCode.CUSTOM_RUN_CREATED, CustomRunCreateResponse.from(run, passcode));
  }

  @Override
  @Transactional
  public SuccessResponse<SessionRunCreateResponse> saveNewSessionRun(String userId, RunCreateDto runCreateDto) {
    User user = userService.getUserByPublicId(userId);
    Run run = runCommandService.saveNewRun(user, runCreateDto);
    participantService.registerParticipant(user.getId(), run);
    return SuccessResponse.of(RunningHttpResponseCode.SESSION_RUN_CREATED,SessionRunCreateResponse.from(run));
  }
}
