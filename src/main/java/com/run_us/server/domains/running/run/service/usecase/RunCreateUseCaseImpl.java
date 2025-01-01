package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.service.CrewService;
import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunCommandService;
import com.run_us.server.domains.running.run.service.RunValidator;
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
  private final CrewService crewService;
  private final RunValidator runValidator;

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
    Run savedRun = createCrewRunOrDefault(user, runCreateDto);
    participantService.registerParticipant(user.getId(), savedRun);
    return SuccessResponse.of(RunningHttpResponseCode.SESSION_RUN_CREATED,SessionRunCreateResponse.from(savedRun));
  }

  // ONLY_CREW인 경우에는 Crew를 찾아서 Run을 생성한다. 그렇지 않은 경우에는 일반 Run을 생성한다.
  private Run createCrewRunOrDefault(User user, RunCreateDto runCreateDto) {
    if (runValidator.isCrewRunCreateCommand(runCreateDto)) {
      Crew crew = crewService.getCrewByPublicId(runCreateDto.getCrewPublicId());
      return runCommandService.saveNewCrewRun(user, crew.getId(), runCreateDto);
    }
    return runCommandService.saveNewRun(user, runCreateDto);
  }
}
