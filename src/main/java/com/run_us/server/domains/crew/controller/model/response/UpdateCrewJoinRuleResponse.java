package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCrewJoinRuleResponse {
    private String crewPublicId;
    private CrewJoinType joinType;
    private String joinQuestion;

    @Builder
    public UpdateCrewJoinRuleResponse(String crewPublicId, CrewJoinType joinType, String joinQuestion) {
        this.crewPublicId = crewPublicId;
        this.joinType = joinType;
        this.joinQuestion = joinQuestion;
    }

    public static UpdateCrewJoinRuleResponse from(Crew crew) {
        return UpdateCrewJoinRuleResponse.builder()
                .crewPublicId(crew.getPublicId())
                .joinType(crew.getJoinType())
                .joinQuestion(crew.getCrewDescription().getJoinQuestion())
                .build();
    }
}
