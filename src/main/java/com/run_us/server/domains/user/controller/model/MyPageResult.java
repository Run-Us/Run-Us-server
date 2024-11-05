package com.run_us.server.domains.user.controller.model;

import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.service.model.MyRunningRecord;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class MyPageResult {
  private final String profileImageUrl;
  private final String nickname;
  private final Integer totalRunningDistanceInMeters;
  private final ZonedDateTime recentRunningDate;
  private final List<MyRunningRecord> runningRecords;

  @Builder
  public MyPageResult(String profileImageUrl, String nickname, Integer totalRunningDistanceInMeters, ZonedDateTime recentRunningDate, List<MyRunningRecord> runningRecords) {
    this.profileImageUrl = profileImageUrl;
    this.nickname = nickname;
    this.totalRunningDistanceInMeters = totalRunningDistanceInMeters;
    this.recentRunningDate = recentRunningDate;
    this.runningRecords = runningRecords;
  }

  public static MyPageResult of(Profile userProfile, List<MyRunningRecord> records) {
    return MyPageResult.builder()
        .profileImageUrl(userProfile.getImgUrl())
        .nickname(userProfile.getNickname())
        .totalRunningDistanceInMeters(userProfile.getTotalDistance())
        .recentRunningDate(records.isEmpty() ? null : records.getFirst().getStartedAt())
        .runningRecords(records)
        .build();
  }
}
