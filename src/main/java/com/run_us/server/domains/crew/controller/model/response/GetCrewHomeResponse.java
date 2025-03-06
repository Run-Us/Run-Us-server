package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import com.run_us.server.domains.crew.service.model.CrewRunCardInfo;
import lombok.AccessLevel;
import lombok.Builder;
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
    private String joinQuestion;
    private CrewThemeType crewType;
    private Integer memberCount;
    private ZonedDateTime createdAt;
    private SimpleUserInfo crewOwner;
    private Boolean existNewJoinRequest;
    private CrewThisMonthRecord thisMonthRecord;
    private RunCardInfo regularRunning;
    private RunCardInfo irregularRunning;

    @Builder
    public GetCrewHomeResponse( // TODO : 가독성 이게 최선인가..
                                String crewPublicId, String title, String profileImg, String location, String intro,
                                CrewJoinType joinType, String joinQuestion, CrewThemeType crewType,
                                Integer memberCount, ZonedDateTime createdAt, SimpleUserInfo crewOwner,
                                Boolean existNewJoinRequest, CrewThisMonthRecord thisMonthRecord,
                                RunCardInfo regularRunning, RunCardInfo irregularRunning
    ) {
        this.crewPublicId = crewPublicId;
        this.title = title;
        this.profileImg = profileImg;
        this.location = location;
        this.intro = intro;
        this.joinType = joinType;
        this.joinQuestion = joinQuestion;
        this.crewType = crewType;
        this.memberCount = memberCount;
        this.createdAt = createdAt;
        this.crewOwner = crewOwner;
        this.existNewJoinRequest = existNewJoinRequest;
        this.thisMonthRecord = thisMonthRecord;
        this.regularRunning = regularRunning;
        this.irregularRunning = irregularRunning;
    }


    public static GetCrewHomeResponse from(Crew crew, boolean existNewJoinRequest, CrewRunCardInfo cardRunInfo) {
        CrewDescription description = crew.getCrewDescription();

        GetCrewHomeResponseBuilder builder = GetCrewHomeResponse.builder()
                .crewPublicId(crew.getPublicId())
                .title(description.getTitle())
                .profileImg(description.getProfileImageUrl())
                .location(description.getLocation())
                .intro(description.getIntro())
                .joinType(crew.getJoinType())
                .joinQuestion(description.getJoinQuestion())
                .crewType(description.getThemeType())
                .memberCount(crew.getMemberCount())
                .createdAt(crew.getCreatedAt())

                .crewOwner(SimpleUserInfo.from(crew.getOwner()))
                .existNewJoinRequest(existNewJoinRequest)
                .thisMonthRecord(CrewThisMonthRecord.from(crew.getMonthlyRecord()));

        if (cardRunInfo.getIrregularRunCard() != null) {
            builder.irregularRunning(cardRunInfo.getIrregularRunCard());
        }
        if (cardRunInfo.getRegularRunCard() != null) {
            builder.regularRunning(cardRunInfo.getRegularRunCard());
        }

        return builder.build();
    }
}
