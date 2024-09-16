package com.run_us.server.domain.running.model;

import lombok.Getter;

@Getter
public class LocationData {
    private final POINT point;
    private final long count;

    public LocationData(double latitude, double longitude, long count) {
        this.point = new POINT(latitude, longitude);
        this.count = count;
    }

    public double getLatitude() {
        return point.latitude;
    }

    public double getLongitude() {
        return point.longitude;
    }

    public double distanceTo(LocationData other) {
        return this.point.distanceTo(other.point);
    }

    public static class POINT {
        private final double latitude;
        private final double longitude;

        public POINT(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        /***
         * 다른 지점 사이의 거리 계산
         * @param other 거리를 계산할 지점
         * @return 두 지점 사이의 거리(미터)
         */
        public double distanceTo(POINT other) {
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