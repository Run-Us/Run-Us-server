package com.run_us.server.domains.running.domain.running;

import com.run_us.server.global.util.JsonConverter;
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

  @Builder
  public RunningConstraints(Integer maxParticipantCount, Integer minPace) {
    this.maxParticipantCount = maxParticipantCount;
    this.minPace = minPace;
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
        && Objects.equals(minPace, that.minPace);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxParticipantCount, minPace);
  }

  public static class RunningConstraintsConverter extends JsonConverter<RunningConstraints> {
  }
}
