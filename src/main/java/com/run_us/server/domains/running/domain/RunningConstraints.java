package com.run_us.server.domains.running.domain;

import com.run_us.server.global.utils.JsonConverter;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RunningConstraints {
  private Integer maxParticipantCount;
  private Integer minPace;
  private String ageGroup;

  @Builder
  public RunningConstraints(Integer maxParticipantCount, Integer minPace, String ageGroup) {
    this.maxParticipantCount = maxParticipantCount;
    this.minPace = minPace;
    this.ageGroup = ageGroup;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RunningConstraints that = (RunningConstraints) o;
    return Objects.equals(maxParticipantCount, that.maxParticipantCount)
        && Objects.equals(minPace, that.minPace) && Objects.equals(ageGroup,
        that.ageGroup);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxParticipantCount, minPace, ageGroup);
  }

  public static class RunningConstraintsConverter extends JsonConverter<RunningConstraints> {

  }
}
