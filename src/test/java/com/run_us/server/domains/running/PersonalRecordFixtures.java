package com.run_us.server.domains.running;

import com.run_us.server.domains.running.domain.record.PersonalRecord;

public final class PersonalRecordFixtures {

    public static PersonalRecord getPersonalRecordWithUserIdAndRunningId(Long userId, Long runningId) {
        return PersonalRecord.builder()
            .runningId(userId)
            .userId(runningId)
            .recordData("recordData")
            .runningDistanceInMeters(1000)
            .runningDurationInMilliseconds(1000)
            .averagePaceInMilliseconds(1000)
            .build();
    }

}
