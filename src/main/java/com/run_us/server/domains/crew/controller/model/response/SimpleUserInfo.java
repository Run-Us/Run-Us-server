package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.crew.controller.model.enums.CrewErrorCode;
import com.run_us.server.domains.crew.controller.model.enums.CrewException;
import com.run_us.server.domains.user.domain.Profile;
import com.run_us.server.domains.user.domain.User;
import com.run_us.server.domains.user.exception.UserErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SimpleUserInfo {
    private String userPublicId;
    private String profileImageUrl;
    private String nickname;

    @Builder
    public SimpleUserInfo(String userPublicId, String profileImageUrl, String nickname) {
        this.userPublicId = userPublicId;
        this.profileImageUrl = profileImageUrl;
        this.nickname = nickname;
    }

    public static SimpleUserInfo from(User user) {
        Profile profile = user.getProfile();
        if(profile == null){
            throw new CrewException(UserErrorCode.GET_USER_PROFILE_FAILED);
        }

        return SimpleUserInfo.builder()
                .userPublicId(user.getPublicId())
                .profileImageUrl(profile.getImgUrl())
                .nickname(profile.getNickname())
                .build();
    }
}
