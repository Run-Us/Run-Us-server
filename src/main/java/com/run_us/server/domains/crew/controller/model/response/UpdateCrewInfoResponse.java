package com.run_us.server.domains.crew.controller.model.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCrewInfoResponse {
    private String crewPublicId;

    @Builder
    public UpdateCrewInfoResponse(String crewPublicId) {
        this.crewPublicId = crewPublicId;
    }

    public static UpdateCrewInfoResponse from(String crewPublicId) {
        return UpdateCrewInfoResponse.builder()
                .crewPublicId(crewPublicId)
                .build();
    }
}
