package com.run_us.server.domains.running;

import com.run_us.server.domains.running.run.controller.model.request.SessionAccessLevel;
import com.run_us.server.domains.running.run.domain.Run;
import com.run_us.server.domains.running.run.domain.RunPace;
import com.run_us.server.domains.running.run.domain.RunningPreview;

import java.time.ZonedDateTime;
import java.util.List;

public final class RunFixtures {

  public static Run createRun() {
    Run run = new Run(1);
    run.modifySessionInfo(createRunningPreview());
    return run;
  }

  private static RunningPreview createRunningPreview() {
    return RunningPreview.builder()
        .title("title")
        .description("description")
        .meetingPoint("meetingPlace")
        .paceCategories(List.of(RunPace.PACE_UNDER_500))
        .accessLevel(SessionAccessLevel.ALLOW_ALL)
        .beginTime(ZonedDateTime.now())
        .build();
  }
}
