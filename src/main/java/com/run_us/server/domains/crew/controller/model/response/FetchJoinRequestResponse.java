package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.domain.CrewJoinRequest;
import com.run_us.server.domains.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FetchJoinRequestResponse {
    private Integer requestId;
    private String userPublicId;
    private String nickname;
    private String profileImg;
    private String message;

    public static FetchJoinRequestResponse from(CrewJoinRequest request, User userInfo) {
        return new FetchJoinRequestResponse(
                request.getId(),
                userInfo.getPublicId(),
                userInfo.getProfile().getNickname(),
                userInfo.getProfile().getImgUrl(),
                request.getAnswer()
        );
    }
}
