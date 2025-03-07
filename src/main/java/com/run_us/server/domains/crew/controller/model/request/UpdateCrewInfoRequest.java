package com.run_us.server.domains.crew.controller.model.request;

import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCrewInfoRequest {
    private String title;
    private String intro;
    private String location;
    private String profileImageUrl;
    private CrewThemeType crewType;

    public CrewDescription from(CrewDescription old) {
        return CrewDescription.builder()
                .title(this.title)
                .intro(this.intro)
                .location(this.location)
                .profileImageUrl(this.profileImageUrl)
                .themeType(this.crewType)
                .joinQuestion(old.getJoinQuestion())
                .build();
    }
}
