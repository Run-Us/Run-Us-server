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
     * @return Crew 엔티티
     */
    public static Crew createInitalCrew(String ownerNickname, String crewTitle, String crewIntro) {
        User owner = UserFixtures.getDefaultUserWithNickname(ownerNickname);
      return Crew.builder()
              .owner(owner)
              .crewDescription(createCrewDescription(crewTitle, crewIntro))
              .crewMemberships(createOwnerOnlyCrewMemberships(owner))
              .joinType(CrewJoinType.OPEN)
              .build();
    }

    public static Crew createCrewWithOwner(User owner, String crewTitle, String crewIntro) {
      return Crew.builder()
              .owner(owner)
              .crewDescription(createCrewDescription(crewTitle, crewIntro))
              .crewMemberships(createOwnerOnlyCrewMemberships(owner))
              .joinType(CrewJoinType.OPEN)
              .build();
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
     * @param user 크루장
     * @return 크루장만 포함된 크루 멤버십 목록
     */
    private static List<CrewMembership> createOwnerOnlyCrewMemberships(User user) {
        CrewMembership ownerMembership = CrewMembership.builder()
                .userId(user.getId())
                .role(CrewMembershipRole.OWNER)
                .build();
        return List.of(ownerMembership);
    }
}
