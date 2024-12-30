package com.run_us.server.domains.crew.controller.model.request;

import com.run_us.server.domains.crew.domain.enums.CrewJoinRequestStatus;
import com.run_us.server.global.validator.annotation.EnumValid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewJoinRequest {
    @NotNull
    @EnumValid(enumClass = CrewJoinRequestStatus.class)
    private String status;
}
