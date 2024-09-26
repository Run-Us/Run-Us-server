package com.run_us.server.domains.running;

import com.run_us.server.domains.running.domain.Running;
import com.run_us.server.domains.running.domain.RunningConstraints;
import com.run_us.server.domains.running.domain.RunningDescription;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.global.utils.PointGenerator;
import java.util.List;
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

  public static Running getDefaultRunningWithParticipants(List<User> users) {
    Running running = getDefaultRunning();
    users.forEach(running::addParticipant);
    return running;
  }
}
