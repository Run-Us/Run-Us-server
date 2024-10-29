package com.run_us.server.global.common;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PreUpdate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class DateAudit extends CreationTimeAudit {

  protected ZonedDateTime updatedAt;

  @Override
  protected void prePersist() {
    super.prePersist();
    this.updatedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
  }

  @PreUpdate
  protected void preUpdate() {
    this.updatedAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
  }
}
