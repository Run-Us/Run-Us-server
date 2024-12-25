package com.run_us.server.domains.crew.controller.model.request;

import com.run_us.server.domains.crew.domain.Crew;
import com.run_us.server.domains.crew.domain.CrewDescription;
import com.run_us.server.domains.crew.domain.CrewMembership;
import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewMembershipRole;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import com.run_us.server.domains.user.domain.User;
import lombok.*;

import java.util.List;

// TODO : 필드별 제약조건 설정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateCrewRequest {
    private String title;
    private String profileImage;
    private String location;
    private String intro;
    private String crewType;
    private String joinType;
    private String joinQuestion;
    private String ownerId;

    public Crew toEntity(User creator) {
        CrewDescription description = CrewDescription.builder()
                .title(this.title)
                .profileImageUrl(this.profileImage)
                .location(this.location)
                .intro(this.intro)
                .themeType(CrewThemeType.valueOf(this.crewType))
                .joinQuestion(this.joinQuestion)
                .build();
        CrewMembership ownerMembership = CrewMembership.builder()
                .userId(creator.getId())
                .role(CrewMembershipRole.OWNER)
                .build();
        return Crew.builder()
                .crewDescription(description)
                .joinType(CrewJoinType.valueOf(this.joinType))
                .owner(creator)
                .crewMemberships(List.of(ownerMembership))
                .build();
    }
}
