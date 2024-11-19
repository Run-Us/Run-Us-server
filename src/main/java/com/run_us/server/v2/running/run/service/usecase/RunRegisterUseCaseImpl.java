package com.run_us.server.v2.running.run.service.usecase;

import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.service.UserService;
import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.service.ParticipantService;
import com.run_us.server.v2.running.run.service.RunQueryService;
import com.run_us.server.v2.running.run.service.model.ParticipantInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunRegisterUseCaseImpl implements RunRegisterUseCase{

  private final UserService userService;
  private final RunQueryService runQueryService;
  private final ParticipantService participantService;

  @Override
  public void registerRun(String userId, String runPublicId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runPublicId);
    participantService.registerParticipant(user.getId(), run);
  }

  @Override
  public void cancelRun(String userId, String runPublicId) {
    User user = userService.getUserByPublicId(userId);
    Run run = runQueryService.findByRunPublicId(runPublicId);
    participantService.cancelParticipant(user.getId(), run);
  }

  @Override
  public List<ParticipantInfo> getRunParticipants(String runPublicId) {
    Run run = runQueryService.findByRunPublicId(runPublicId);
    return participantService.getParticipants(run);
  }
}
