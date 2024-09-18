package com.run_us.server.domains.running.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class RunningResponse {

    /**
     * 러닝 위치 전송 response dto
     *
     * (매번 실시간으로 주면 부하 심하지 않을까? -> 모아뒀다가 보내줘야 하나 해서 일단 만들어봄..)
     */
    @Getter
    @ToString
    public static class UpdateLocation {
        private List<RunningRequest.LocationData> locations;

        @Builder
        public UpdateLocation(List<RunningRequest.LocationData> locations) {
            this.locations = locations;
        }
    }

    /**
     * 러닝 위치 전송 response dto
     */
    @Getter
    @ToString
    public static class LocationData {
        private String userKey; // TODO : 추후 User 쪽에 간단한 프로필 응답 response dto 만들어서 넣기
        private Float x;
        private Float y;

        @Builder
        public LocationData(String userKey, Float x, Float y) {
            this.userKey = userKey;
            this.x = x;
            this.y = y;
        }

        public static LocationData toDto(RunningRequest.LocationData data) {
            return LocationData.builder()
                    .userKey(data.getUserKey())
                    .x(data.getX())
                    .y(data.getY())
                    .build();
        }
    }
}
