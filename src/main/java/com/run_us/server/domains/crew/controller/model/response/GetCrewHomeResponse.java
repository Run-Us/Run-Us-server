package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetCrewHomeResponse {
    private String crewPublicId;
    private String title;
    private String profileImg;
    private String location;
    private String intro;
    private CrewJoinType joinType;
    private String crewJoinQuestion;
    private CrewThemeType crewType;
    private Integer memberCount;
    private ZonedDateTime createdAt;
    private SimpleUserInfo crewOwner;

    private Boolean existNewJoinRequest;

    private CrewThisMonthRecord thisMonthRecord;

    private RunningCardInfo regularRunning;
    private RunningCardInfo irregularRunning;

}
