package com.run_us.server.domains.crew.controller.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancelJoinRequestResponse {
    private final Integer requestId;
}
