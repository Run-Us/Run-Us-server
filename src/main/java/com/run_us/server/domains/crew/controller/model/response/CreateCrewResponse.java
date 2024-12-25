package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.Crew;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCrewResponse {
    private String crewPublicId;

    @Builder
    private CreateCrewResponse(String crewPublicId) {
        this.crewPublicId = crewPublicId;
    }

    public static CreateCrewResponse from(Crew crew) {
        return CreateCrewResponse.builder()
                .crewPublicId(crew.getPublicId())
                .build();
    }

    // builder?
}
