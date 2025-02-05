package com.run_us.server.domains.crew.controller.model.request;

import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCrewJoinTypeRequest {
    private CrewJoinType joinType;
    private String joinQuestion;
}
