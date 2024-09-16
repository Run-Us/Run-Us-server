package com.run_us.server.domain.running.model;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class RunningDescription {

  private String title;
  private String desc;
  private String distance;
  private String runningTime;

  @Builder
  public RunningDescription(String title, String desc, String distance, String runningTime) {
    this.title = title;
    this.desc = desc;
    this.distance = distance;
    this.runningTime = runningTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunningDescription that = (RunningDescription) o;
    return Objects.equals(title, that.title) && Objects.equals(desc, that.desc)
        && Objects.equals(distance, that.distance) && Objects.equals(runningTime,
        that.runningTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(title, desc, distance, runningTime);
  }
}