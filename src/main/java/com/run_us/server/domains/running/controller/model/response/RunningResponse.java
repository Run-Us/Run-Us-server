package com.run_us.server.domains.running.controller.model.response;

import com.run_us.server.domains.running.controller.model.request.RunningRequest.LocationUpdate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class RunningResponse {

    /**
     * 러닝 위치 전송 response dto
     *
     * (매번 실시간으로 주면 부하 심하지 않을까? -> 모아뒀다가 보내줘야 하나 해서 일단 만들어봄..)
     */
    @Getter
    @ToString
    public static class UpdateLocation {
        private List<LocationUpdate> locations;

        @Builder
        public UpdateLocation(List<LocationUpdate> locations) {
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

        public static LocationData toDto(LocationUpdate data, String userId) {
            return LocationData.builder()
                    .userKey(userId)
                    .x(data.getLatitude())
                    .y(data.getLongitude())
                    .build();
        }
    }
}
