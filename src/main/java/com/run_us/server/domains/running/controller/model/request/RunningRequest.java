package com.run_us.server.domains.running.controller.model.request;

import com.run_us.server.domains.running.domain.LocationData;
import java.util.List;
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
        private String userId;
        private String runningId;
        private String runningKey;

        // Websocket - start
        // inner class의 효능
        // 난잡한 디렉구조 해결
        @Builder
        public StartRunning(String userId, String runningId, String runningKey) {
            this.userId = userId;
            this.runningId = runningId;
            this.runningKey = runningKey;
        }
    }
    // DTO e다 분리 / inner class

    /**
     * 러닝 위치 전송 request dto
     */
    @Getter
    @ToString
    public static class LocationUpdate {
        private final String runningId;
        private final String userId; // TODO : 추후 User 쪽에 간단한 프로필 응답 response dto 만들어서 넣기
        private final Float latitude;
        private final Float longitude;
        private final int count;

        @Builder
        public LocationUpdate(final String runningId, final String userId, final Float latitude, final Float longitude, final int count) {
            this.runningId = runningId;
            this.userId = userId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.count = count;
        }
    }

    @Getter
    @ToString
    public static class PauseRunning {
       private final String runningId;
       private final String userId;
       private final int count;

       @Builder
        public PauseRunning(final String runningKey, final String userKey, final int count) {
            this.runningId = runningKey;
            this.userId = userKey;
            this.count = count;
        }
    }

    @Getter
    @ToString
    public static final class ResumeRunning {

        private final String runningId;
        private final String userId;
        private final int count;

        @Builder
        public ResumeRunning(final String runningId, final String userId, final int count) {
            this.runningId = runningId;
            this.userId = userId;
            this.count = count;
        }
    }

    @Getter
    public static class StopRunning {

        private final String runningId;
        private final String userId;
        private final int count;

        @Builder
        public StopRunning(final String runningId, final String userId, final int count) {
            this.runningId = runningId;
            this.userId = userId;
            this.count = count;
        }
    }

    @Getter
    public static class AggregateRunning {

        private final String runningId;
        private final String userId;
        private final List<LocationData> dataList;
        private final int count;

        @Builder
        public AggregateRunning(final String runningId, final String userId, final int count, final List<LocationData> dataList) {
            this.runningId = runningId;
            this.userId = userId;
            this.count = count;
            this.dataList = dataList;
        }

    }
}
