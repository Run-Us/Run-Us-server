package com.run_us.server.domain.running;

import com.run_us.server.domain.running.model.Running;
import com.run_us.server.domain.running.model.RunningConstraints;
import com.run_us.server.domain.running.model.RunningDescription;
import com.run_us.server.global.utils.PointGenerator;
import org.locationtech.jts.geom.Point;

public final class RunningFixtures {

  public static final Point DEFAULT_START_LOCATION = PointGenerator.generatePoint(0L, 0L);

  public static Running getDefaultRunning() {
    return Running.builder()
        .startLocation(DEFAULT_START_LOCATION)
        .constraints(getDefaultRunningConstraints())
        .description(getDefaultRunningDescription())
        .build();
  }

  public static RunningConstraints getDefaultRunningConstraints() {
    return RunningConstraints.builder()
        .maxParticipantCount(10)
        .ageGroup("20-29")
        .minPace(6)
        .build();
  }

  public static RunningDescription getDefaultRunningDescription() {
    return RunningDescription.builder()
        .runningTime("2021-10-10 10:00:00")
        .title("DEFAULT_TITLE")
        .distance("10KM")
        .desc("test description")
        .build();
  }
}
