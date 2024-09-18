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


    /**
     * 러닝 위치 전송 request dto
     */
    @Getter
    @ToString
    public static class LocationData {
        private String runningKey;
        private String userKey; // TODO : 추후 User 쪽에 간단한 프로필 응답 response dto 만들어서 넣기
        private Float x;
        private Float y;

        @Builder
        public LocationData(String runningKey, String userKey, Float x, Float y) {
            this.runningKey = runningKey;
            this.userKey = userKey;
            this.x = x;
            this.y = y;
        }
    }
}
