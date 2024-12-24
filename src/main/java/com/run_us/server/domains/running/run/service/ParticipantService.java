package com.run_us.server.domains.running.run.service;

import com.run_us.server.domains.running.common.RunningErrorCode;
import com.run_us.server.domains.running.common.RunningException;
import com.run_us.server.domains.running.run.domain.Participant;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.repository.ParticipantRepository;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ParticipantService {

  private final ParticipantRepository participantRepository;
  private final RunQueryService runQueryService;

  public void registerParticipant(Integer userId, Run run) {
    Participant participant = new Participant(userId, run);
    participantRepository.save(participant);
  }

  public void joinLiveRunning(Integer userId, Run run) {
    Participant participant =
        participantRepository
            .findByUserIdAndRunId(userId, run.getId())
            .orElse(new Participant(userId, run));
    participant.attend();
    participantRepository.save(participant);
  }

  public void cancelParticipant(Integer userId, Run run) {
    Participant participant =
        participantRepository
            .findByUserIdAndRunId(userId, run.getId())
            .orElseThrow(() -> RunningException.of(RunningErrorCode.USER_NOT_JOINED));
    participantRepository.delete(participant);
  }

  public List<ParticipantInfo> getParticipants(Run run) {
    return participantRepository.findByRunId(run.getId());
  }
}
