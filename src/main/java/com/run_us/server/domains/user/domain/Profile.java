package com.run_us.server.domains.user.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor
public class Profile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Setter
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "nickname", nullable = false)
  private String nickname;

  @Column(name = "img_url")
  private String imgUrl;

  @Column(name = "birth_date")
  private LocalDate birthDate;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private Gender gender;

  @Column(name = "pace")
  private Integer pace;

  @Column(name = "total_distance", nullable = false)
  private Integer totalDistance = 0;

  @Column(name = "total_time", nullable = false)
  private Integer totalTime = 0;

  @Column(name = "longest_distance", nullable = false)
  private Integer longestDistance = 0;

  @Column(name = "longest_time", nullable = false)
  private Integer longestTime = 0;

  @Builder
  public Profile(
      Integer userId,
      String nickname,
      String imgUrl,
      LocalDate birthDate,
      Gender gender,
      Integer pace) {
    this.userId = userId;
    this.nickname = nickname;
    this.imgUrl = imgUrl;
    this.birthDate = birthDate;
    this.gender = gender;
    this.pace = pace;
  }

  public void updateRunningInfos(int runningDistanceInMeters, int runningDurationInMilliSeconds) {
    this.totalDistance += runningDistanceInMeters;
    this.totalTime += runningDurationInMilliSeconds;
    updateLongestDistance(runningDistanceInMeters);
    updateLongestTime(runningDurationInMilliSeconds);
  }

  private void updateLongestDistance(int runningDistanceInMeters) {
    this.longestDistance = Math.max(this.longestDistance, runningDistanceInMeters);
  }

  private void updateLongestTime(int runningDurationInMilliSeconds) {
    this.longestTime = Math.max(this.longestTime, runningDurationInMilliSeconds);
  }
}
