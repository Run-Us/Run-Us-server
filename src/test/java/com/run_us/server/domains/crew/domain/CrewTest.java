package com.run_us.server.domains.crew.domain;

import com.run_us.server.domains.crew.domain.enums.CrewMembershipRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrewTest {

    @DisplayName("크루 초기 생성 테스트")
    @Test
    void test_create_initial_crew() {
        String ownerNickname = "달려라 하니";
        String crewTitle = "빵빵런";
        String crewIntro = "러닝하고 빵먹고";
        Crew crew = CrewFixtures.createInitalCrew(ownerNickname, crewTitle, crewIntro);

        assertNotNull(crew);
        assertEquals(crew.getOwner().getProfile().getNickname(), ownerNickname);
        assertEquals(crew.getCrewDescription().getTitle(), crewTitle);
        assertEquals(crew.getCrewDescription().getIntro(), crewIntro);
        assertEquals(crew.getCrewMemberships().size(), 1);
        assertEquals(crew.getCrewMemberships().get(0).getRole(), CrewMembershipRole.OWNER);
    }

}