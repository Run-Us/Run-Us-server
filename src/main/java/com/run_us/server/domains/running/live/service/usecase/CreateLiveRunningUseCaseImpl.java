package com.run_us.server.domains.running.live.service.usecase;

import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.live.service.model.LiveRunningCreateResponse;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.running.run.service.RunValidator;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import com.run_us.server.domains.user.domain.UserPrincipal;
import com.run_us.server.domains.user.service.resolver.UserIdResolver;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateLiveRunningUseCaseImpl implements CreateLiveRunningUseCase {

  private final RunQueryService runQueryService;
  private final RunValidator runValidator;
  private final ParticipantService participantService;
  private final PassCodeRegistry passCodeRegistry;
  private final UserIdResolver userIdResolver;

  @Override
  @Transactional
  public SuccessResponse<LiveRunningCreateResponse> createLiveRunning(String runPublicId, String userPublicId) {
    UserPrincipal UserPrincipal = userIdResolver.resolve(userPublicId);
    Run selectedRun = runQueryService.findByRunPublicId(runPublicId);
    runValidator.validateCurrentUserCanStartRun(UserPrincipal.getInternalId(), selectedRun);
    participantService.joinLiveRunning(UserPrincipal.getInternalId(), selectedRun);
    String passcode = passCodeRegistry.generateAndGetPassCode(runPublicId);
    selectedRun.openLiveSession(UserPrincipal.getInternalId());
    List<ParticipantInfo> participantInfos = participantService.getParticipants(selectedRun);
    return SuccessResponse.of(RunningHttpResponseCode.LIVE_ROOM_CREATED, LiveRunningCreateResponse.from(selectedRun, passcode, participantInfos));
  }


}
