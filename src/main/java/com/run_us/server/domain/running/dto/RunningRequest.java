package com.run_us.server.domain.running.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class RunningRequest {

    /**
     * 러닝 시작 dto (프로토타입용 임시 dto)
     */
    @Getter
    @ToString
    public static class StartRunning {
        private String runningKey;

        @Builder
        StartRunning(String runningKey) {
            this.runningKey = runningKey;
        }

    }


}
