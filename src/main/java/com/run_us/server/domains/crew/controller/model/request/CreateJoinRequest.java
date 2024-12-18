package com.run_us.server.domains.crew.controller.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateJoinRequest {
    @NotNull
    @Length(max = 100)
    private String answer;

    @Builder
    public CreateJoinRequest(String answer) {
        this.answer = answer;
    }
}
