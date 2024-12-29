package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.Participant;
import com.run_us.server.domains.running.run.domain.Run;

public final class ParticipantFixtures {

  public static Participant createParticipantWithRunAndUserId(Run run, Integer userId) {
    return new Participant(userId, run);
  }
}
