package com.run_us.server.domains.running.controller.model.request;

import com.run_us.server.domains.running.domain.LocationData;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class RunningRequest {

    /**
     * 러닝 시작 dto
     */
    @Getter
    @ToString
    public static class StartRunning {
        private final String runningId;

        @Builder
        public StartRunning(String runningId) {
            this.runningId = runningId;
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
        private final Float latitude;
        private final Float longitude;
        private final int count;

        @Builder
        public LocationUpdate(final String runningId, final Float latitude, final Float longitude, final int count) {
            this.runningId = runningId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.count = count;
        }
    }

    @Getter
    @ToString
    public static class PauseRunning {
       private final String runningId;
       private final int count;

       @Builder
        public PauseRunning(final String runningKey, final int count) {
            this.runningId = runningKey;
            this.count = count;
        }
    }

    @Getter
    @ToString
    public static final class ResumeRunning {

        private final String runningId;
        private final int count;

        @Builder
        public ResumeRunning(final String runningId, final int count) {
            this.runningId = runningId;
            this.count = count;
        }
    }

    @Getter
    public static class StopRunning {

        private final String runningId;
        private final int count;

        @Builder
        public StopRunning(final String runningId, final int count) {
            this.runningId = runningId;
            this.count = count;
        }
    }

    @Getter
    public static class AggregateRunning {

        private final String runningId;
        private final List<LocationData> dataList;
        private final int count;
        private final int runningDistanceInMeter;
        private final int runningDurationInMilliSecond;
        private final int averagePaceInMilliSecond;

        @Builder
        public AggregateRunning(
                final String runningId,
                final int count,
                final List<LocationData> dataList,
                final int runningDistanceInMeter,
                final int runningDurationInMilliSecond,
                final int averagePaceInMilliSecond) {
            this.runningId = runningId;
            this.count = count;
            this.dataList = dataList;
            this.runningDistanceInMeter = runningDistanceInMeter;
            this.runningDurationInMilliSecond = runningDurationInMilliSecond;
            this.averagePaceInMilliSecond = averagePaceInMilliSecond;
        }
    }
}
