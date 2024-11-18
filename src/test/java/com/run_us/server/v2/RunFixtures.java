package com.run_us.server.v2;

import com.run_us.server.v2.running.run.domain.Run;
import com.run_us.server.v2.running.run.domain.RunPace;
import com.run_us.server.v2.running.run.domain.RunningPreview;

import java.time.ZonedDateTime;

public final class RunFixtures {

  public static Run createRun() {
    Run run = new Run(1);
    run.modifySessionInfo(createRunningPreview());
    return run;
  }

  private static RunningPreview createRunningPreview() {
    return new RunningPreview(
        "title",
        "description",
        "meetingPlace",
        RunPace.PACE_UNDER_500,
        "goal",
        "visibility",
        ZonedDateTime.now()
    );
  }
}
