package com.run_us.server.domains.running.run.service.model;

import lombok.Getter;

@Getter
public class HostInfo {
  private String userPublicId;
  private String name;
  private String imgUrl;

  public HostInfo(String userPublicId, String name, String imgUrl) {
    this.userPublicId = userPublicId;
    this.name = name;
    this.imgUrl = imgUrl;
  }
}
