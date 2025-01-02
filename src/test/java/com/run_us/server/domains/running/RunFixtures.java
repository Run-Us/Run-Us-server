package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.domain.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunningPreview;

import java.time.ZonedDateTime;

public final class RunFixtures {

  public static Run createRun() {
    Run run = new Run(null);
    run.modifySessionInfo(createRunningPreview());
    return run;
  }

  public static Run createRunWithCrewIdAndAccessLevel(Integer crewId, SessionAccessLevel accessLevel) {
    Run run = new Run(null, crewId);
    run.modifySessionInfo(createRunningPreviewWithAccessLevel(accessLevel));
    return run;
  }

  private static RunningPreview createRunningPreview() {
    return RunningPreview.builder()
        .title("title")
        .description("description")
        .meetingPoint("meetingPlace")
        .accessLevel(SessionAccessLevel.ALLOW_ALL)
        .beginTime(ZonedDateTime.now())
        .build();
  }

  private static RunningPreview createRunningPreviewWithAccessLevel(SessionAccessLevel accessLevel) {
    return RunningPreview.builder()
        .title("title")
        .description("description")
        .meetingPoint("meetingPlace")
        .accessLevel(accessLevel)
        .beginTime(ZonedDateTime.now())
        .build();
  }
}
