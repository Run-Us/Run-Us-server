package com.run_us.server.v2.running.run.service;

import com.run_us.server.domains.running.exception.RunningErrorCode;
import com.run_us.server.v2.RunningException;
import com.run_us.server.v2.running.run.domain.Participant;
import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.repository.ParticipantRepository;
import com.run_us.server.v2.running.run.service.model.ParticipantInfo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ParticipantService {

  private final ParticipantRepository participantRepository;

  public void registerParticipant(Integer userId, Run run) {
    Participant participant = new Participant(userId, run);
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