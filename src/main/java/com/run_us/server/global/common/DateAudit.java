package com.run_us.server.global.common;

import static com.run_us.server.global.common.GlobalConsts.TIME_ZONE_ID;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Data;

@MappedSuperclass
@Data
public class DateAudit implements Serializable {

  protected ZonedDateTime createdAt;

  protected ZonedDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
    this.updatedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = ZonedDateTime.now();
  }
}
