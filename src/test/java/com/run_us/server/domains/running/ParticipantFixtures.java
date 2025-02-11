package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.Participant;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;

import java.util.ArrayList;
import java.util.List;

public final class ParticipantFixtures {

  public static Participant createParticipantWithRunAndUserId(Run run, Integer userId) {
    return new Participant(userId, run);
  }

  public static List<ParticipantInfo> createParticipantInfos() {
    List<ParticipantInfo> participants = new ArrayList<>();
    participants.add(new ParticipantInfo("example1", "default", 1000));
    participants.add(new ParticipantInfo("example2", "default", 2000));
    participants.add(new ParticipantInfo("example3", "default", 3000));
    return participants;
  }
}
