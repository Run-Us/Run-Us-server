package com.run_us.server.domains.running.domain;

import lombok.Getter;

@Getter
public class LocationData {
    private final RunnerPos runnerPos;
    private final long count;

    public LocationData(double latitude, double longitude, long count) {
        this.runnerPos = new RunnerPos(latitude, longitude);
        this.count = count;
    }

    public double getLatitude() {
        return runnerPos.latitude;
    }

    public double getLongitude() {
        return runnerPos.longitude;
    }

    @Getter
    public static class RunnerPos {
        private final double latitude;
        private final double longitude;

        public RunnerPos(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        // 생성 메소드
        public static RunnerPos of(final double latitude, final double longitude) {
            return new RunnerPos(latitude, longitude);
        }

        // 거리 계산 메소드

        /***
         * 다른 지점 사이의 거리 계산
         * @param other 거리를 계산할 지점
         * @return 두 지점 사이의 거리(미터)
         */
        public double distanceTo(RunnerPos other) {
            double R = 6371e3;
            double lat1 = Math.toRadians(this.latitude);
            double lat2 = Math.toRadians(other.latitude);
            double deltaLat = Math.toRadians(other.latitude - this.latitude);
            double deltaLon = Math.toRadians(other.longitude - this.longitude);

            double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
                    Math.cos(lat1) * Math.sin(deltaLon / 2) * Math.cos(lat2) * Math.sin(deltaLon / 2);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

            return R * c;
        }
    }
}