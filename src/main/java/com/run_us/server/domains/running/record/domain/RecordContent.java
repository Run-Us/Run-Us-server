package com.run_us.server.domains.running.record.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecordContent implements Serializable {
  private String title;
  private String description;
  private String pathImageUrl;

  public RecordContent(String title, String description, String pathImageUrl) {
    this.title = title;
    this.description = description;
    this.pathImageUrl = pathImageUrl;
  }
}
