package com.run_us.server.domains.running.service.model;

import lombok.Getter;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;

@Getter
public class PersonalRecordQueryResult {
    private final Integer runningDistanceInMeters;

    private final Integer runningDurationInMilliseconds;

    private final Integer averagePaceInMilliseconds;

    private final ZonedDateTime startedAt;

    public PersonalRecordQueryResult(Integer runningDistanceInMeters,
                                     Integer runningDurationInMilliseconds,
                                     Integer averagePaceInMilliseconds,
                                     ZonedDateTime createdAt) {
        this.runningDistanceInMeters = runningDistanceInMeters;
        this.runningDurationInMilliseconds = runningDurationInMilliseconds;
        this.averagePaceInMilliseconds = averagePaceInMilliseconds;
        this.startedAt = createdAt.minus(Duration.of(averagePaceInMilliseconds, TimeUnit.MILLISECONDS.toChronoUnit()));
    }
}
