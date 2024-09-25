package com.run_us.server.domains.running.controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinedParticipantsDto {

  private String name;
  private String imgUrl;

  @Builder
  public JoinedParticipantsDto(String name, String imgUrl) {
    this.name = name;
    this.imgUrl = imgUrl;
  }
}
