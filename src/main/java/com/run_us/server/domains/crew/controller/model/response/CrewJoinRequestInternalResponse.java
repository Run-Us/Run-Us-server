package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class CrewJoinRequestInternalResponse {
    private final Integer id;
    private final String crewPublicId;
    private final Integer userInternalId;
    private final CrewJoinRequestStatus status;
    private final ZonedDateTime requestedAt;

    public CreateJoinRequestResponse toPublicCreateResponse(String userPublicId) {
        return CreateJoinRequestResponse.builder()
                .requestId(this.id)
                .crewPublicId(this.crewPublicId)
                .userPublicId(userPublicId)
                .status(this.status)
                .requestedAt(this.requestedAt)
                .build();
    }
}
