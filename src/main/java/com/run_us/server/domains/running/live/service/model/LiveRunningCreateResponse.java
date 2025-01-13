package com.run_us.server.domains.running.live.service.model;

import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.service.model.ParticipantInfo;
import lombok.Getter;

import java.util.List;

@Getter
public class LiveRunningCreateResponse {
  private final String runPublicId;
  private final String passcode;
  private final List<ParticipantInfo> participantInfos;

  public LiveRunningCreateResponse(String runPublicId, String passcode, List<ParticipantInfo> participantInfos) {
    this.runPublicId = runPublicId;
    this.passcode = passcode;
    this.participantInfos = participantInfos;
  }
  public static LiveRunningCreateResponse from (Run run, String passcode, List<ParticipantInfo> participantInfos) {
    return new LiveRunningCreateResponse(run.getPublicId(), passcode, participantInfos);
  }
}
