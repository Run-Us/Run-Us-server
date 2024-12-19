package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
public class CreateJoinRequestResponse {
    private final Integer requestId;
    private final String crewPublicId;
    private final String userPublicId;
    private final CrewJoinRequestStatus status;
    private final ZonedDateTime requestedAt;
}
