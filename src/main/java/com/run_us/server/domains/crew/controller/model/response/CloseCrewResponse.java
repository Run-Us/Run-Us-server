package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.Crew;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CloseCrewResponse {
    private String crewPublicId;

    @Builder
    private CloseCrewResponse(String crewPublicId) {
        this.crewPublicId = crewPublicId;
    }

    public static CloseCrewResponse from(Crew crew) {
        return CloseCrewResponse.builder()
                .crewPublicId(crew.getPublicId())
                .build();
    }
}
