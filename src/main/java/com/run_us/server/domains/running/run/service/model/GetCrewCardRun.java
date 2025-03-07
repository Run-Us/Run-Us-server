package com.run_us.server.domains.running.run.service.model;

import com.run_us.server.domains.running.run.domain.Run;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GetCrewCardRun {
    Run regularRun;
    Run irregularRun;

    @Builder(access = AccessLevel.PRIVATE)
    public GetCrewCardRun(Run regularRun, Run irregularRun) {
        this.regularRun = regularRun;
        this.irregularRun = irregularRun;
    }

    public static GetCrewCardRun from(Run regularRun, Run irregularRun) {
        GetCrewCardRunBuilder builder = GetCrewCardRun.builder();

        if (regularRun != null) {
            builder.regularRun(regularRun);
        }
        if (irregularRun != null) {
            builder.irregularRun(irregularRun);
        }

        return builder.build();
    }
}
