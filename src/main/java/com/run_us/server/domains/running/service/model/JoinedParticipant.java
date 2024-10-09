package com.run_us.server.domains.running.service.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinedParticipant {

  private String name;
  private String imgUrl;

  @Builder
  public JoinedParticipant(String name, String imgUrl) {
    this.name = name;
    this.imgUrl = imgUrl;
  }
}
