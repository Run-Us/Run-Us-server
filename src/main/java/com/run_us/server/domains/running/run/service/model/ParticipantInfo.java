package com.run_us.server.domains.running.run.service.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ParticipantInfo {
  private String name;
  private String imgUrl;
  private Integer totalDistanceInMeters;

  public ParticipantInfo(String name, String imgUrl, Integer totalDistanceInMeters) {
    this.name = name;
    this.imgUrl = imgUrl;
    this.totalDistanceInMeters = totalDistanceInMeters;
  }
}
