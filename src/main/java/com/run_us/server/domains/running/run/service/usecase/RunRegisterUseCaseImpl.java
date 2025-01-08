package com.run_us.server.domains.running.run.service.usecase;

import com.run_us.server.domains.running.common.PassCodeRegistry;
import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.controller.model.RunningHttpResponseCode;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.ParticipantService;
import com.run_us.server.domains.running.run.service.RunQueryService;
import com.run_us.server.domains.running.run.service.RunValidator;
import com.run_us.server.domains.running.run.service.model.FetchRunningIdResponse;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.global.common.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RunRegisterUseCaseImpl implements RunRegisterUseCase {

  private final UserService userService;
  private final RunQueryService runQueryService;
  private final RunValidator runValidator;
  private final ParticipantService participantService;
  private final PassCodeRegistry passCodeRegistry;

  @Override
  @Transactional
  public SuccessResponse<List<ParticipantInfo>> registerRun(String userId, String runPublicId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runPublicId);
    //TODO: CREW ONLY라면 CREW인지 확인
    runValidator.validateRunJoinable(run);
    participantService.registerParticipant(user.getId(), run);
    return SuccessResponse.of(RunningHttpResponseCode.PARTICIPANT_REGISTERED, participantService.getParticipants(run));
  }

  @Override
  @Transactional
  public SuccessResponse<Void> cancelRun(String userId, String runPublicId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runPublicId);
    runValidator.validateRunJoinable(run);
    participantService.cancelParticipant(user.getId(), run);
    return SuccessResponse.messageOnly(RunningHttpResponseCode.PARTICIPANT_CANCELED);
  }

  @Override
  @Transactional(readOnly = true)
  public SuccessResponse<List<ParticipantInfo>> getRunParticipants(String runPublicId) {
    Run run = runQueryService.findByRunPublicId(runPublicId);
    return SuccessResponse.of(RunningHttpResponseCode.PARTICIPANTS_FETCHED,participantService.getParticipants(run));
  }

  @Override
  @Transactional(readOnly = true)
  public SuccessResponse<FetchRunningIdResponse> getRunningIdWithPasscode(String passcode) {
    String runId = passCodeRegistry.getRunIdByPassCode(passcode)
        .orElseThrow(() -> RunningException.of(RunningErrorCode.RUNNING_NOT_FOUND));
    return SuccessResponse.of(RunningHttpResponseCode.ROOM_ID_FETCHED, new FetchRunningIdResponse(runId, passcode));
  }
}
