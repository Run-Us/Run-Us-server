package com.run_us.server.domains.user.service.model;

import com.run_us.server.domains.running.record.service.model.RecordPost;
import com.run_us.server.domains.user.domain.Profile;
import java.time.ZonedDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyPageResult {
  private final String profileImageUrl;
  private final String nickname;
  private final Integer totalRunningDistanceInMeters;
  private final ZonedDateTime recentRunningDate;
  private final List<RecordPost> runningRecords;

  @Builder
  public MyPageResult(
      String profileImageUrl,
      String nickname,
      Integer totalRunningDistanceInMeters,
      ZonedDateTime recentRunningDate,
      List<RecordPost> runningRecords) {
    this.profileImageUrl = profileImageUrl;
    this.nickname = nickname;
    this.totalRunningDistanceInMeters = totalRunningDistanceInMeters;
    this.recentRunningDate = recentRunningDate;
    this.runningRecords = runningRecords;
  }

  public static MyPageResult of(Profile userProfile, List<RecordPost> records) {
    return MyPageResult.builder()
        .profileImageUrl(userProfile.getImgUrl())
        .nickname(userProfile.getNickname())
        .totalRunningDistanceInMeters(userProfile.getTotalDistance())
        .recentRunningDate(records.isEmpty() ? null : records.getFirst().getCreatedAt())
        .runningRecords(records)
        .build();
  }
}
