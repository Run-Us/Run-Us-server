package com.run_us.server.global.common;

import static com.run_us.server.global.common.GlobalConst.TIME_ZONE_ID;

import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class CreationTimeAudit implements Serializable {

    protected ZonedDateTime createdAt;

    @PrePersist
    protected void prePersist() {
        this.createdAt = ZonedDateTime.now(ZoneId.of(TIME_ZONE_ID));
    }
}