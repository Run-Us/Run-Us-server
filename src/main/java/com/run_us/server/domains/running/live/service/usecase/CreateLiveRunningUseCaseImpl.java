package com.run_us.server.domains.running.live.service.usecase;

import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.live.service.model.LiveRunningCreateResponse;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.running.run.service.RunValidator;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
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

  @Override
  @Transactional
  public SuccessResponse<LiveRunningCreateResponse> createLiveRunning(String runPublicId, Integer userId) {
    Run selectedRun = runQueryService.findByRunPublicId(runPublicId);
    runValidator.validateCurrentUserCanStartRun(userId, selectedRun);
    participantService.joinLiveRunning(userId, selectedRun);
    String passcode = passCodeRegistry.generateAndGetPassCode(runPublicId);
    selectedRun.openLiveSession(userId);
    List<ParticipantInfo> participantInfos = participantService.getParticipants(selectedRun);
    return SuccessResponse.of(RunningHttpResponseCode.LIVE_ROOM_CREATED, LiveRunningCreateResponse.from(selectedRun, passcode, participantInfos));
  }


}
