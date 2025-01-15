package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.CrewMembership;
import com.run_us.server.domains.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchMemberResponse {
    private String nickname;
    private String publicId;
    private String profileImg;
    private String role;
    private int runningDistance;

    public static FetchMemberResponse from(User user, CrewMembership crewMembership, int runningDistance) {
        return new FetchMemberResponse(
            user.getProfile().getNickname(),
            user.getPublicId(),
            user.getProfile().getImgUrl(),
            crewMembership.getRole().name(),
            runningDistance
        );
    }
}
