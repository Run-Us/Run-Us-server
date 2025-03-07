package com.run_us.server.domains.crew.service.model;

import com.run_us.server.domains.crew.controller.model.request.UpdateCrewInfoRequest;
import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateCrewInfo {
    private String title;
    private String intro;
    private String location;
    private String profileImageUrl;
    private CrewThemeType crewType;

    @Builder
    UpdateCrewInfo(String title, String intro, String location, String profileImageUrl, CrewThemeType crewType){
        this.title = title;
        this.intro = intro;
        this.location = location;
        this.profileImageUrl = profileImageUrl;
        this.crewType = crewType;
    }

    public static UpdateCrewInfo from(UpdateCrewInfoRequest requestDto) {
        return UpdateCrewInfo.builder()
                .title(requestDto.getTitle())
                .intro(requestDto.getIntro())
                .location(requestDto.getLocation())
                .profileImageUrl(requestDto.getProfileImageUrl())
                .crewType(requestDto.getCrewType())
                .build();
    }

    public CrewDescription to(CrewDescription old) {
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
