package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewJoinType;
import com.run_us.server.domains.crew.domain.enums.CrewMembershipRole;
import com.run_us.server.domains.crew.domain.enums.CrewThemeType;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.domain.UserFixtures;

import java.util.List;

public final class CrewFixtures {

    /**
     * 크루장이 크루를 처음 개설했을 때의 Crew 엔티티 반환
     * @return
     */
    public static Crew createInitalCrew(String ownerNickname, String crewTitle, String crewIntro) {
        User owner = UserFixtures.getDefaultUserWithNickname(ownerNickname);
        Crew crew = Crew.builder()
                .owner(owner)
                .crewDescription(createCrewDescription(crewTitle, crewIntro))
                .crewMemberships(createOwnerOnlyCrewMemberships(owner))
                .joinType(CrewJoinType.OPEN)
                .build();
        return crew;
    }

    private static CrewDescription createCrewDescription(String crewTitle, String crewIntro) {
        // 필수값만 넣음
        return CrewDescription.builder()
                .title(crewTitle)
                .intro(crewIntro)
                .location(null)
                .themeType(CrewThemeType.LOCAL_MATE)
                .joinQuestion(null)
                .profileImageUrl(null)
                .build();
    }

    /**
     * 크루장만 포함된 회원 목록 반환
     * @param user
     * @return
     */
    private static List<CrewMembership> createOwnerOnlyCrewMemberships(User user) {
        CrewMembership ownerMembership = CrewMembership.builder()
                .userId(user.getId())
                .role(CrewMembershipRole.OWNER)
                .build();
        return List.of(ownerMembership);
    }
}
