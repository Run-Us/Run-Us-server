package com.run_us.server.domains.crew.controller.model.response;

import com.run_us.server.domains.user.domain.User;
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
//        String profileImgUrl = user.getProfile()==null ? "tempUrl" : user.getProfile().getImgUrl();
//        String profileImgUrl = user.getProfile()==null ? "temp" : user.getProfile().getImgUrl();

        return SimpleUserInfo.builder()
                .userPublicId(user.getPublicId())
                .profileImageUrl(user.getProfile().getImgUrl())
                .nickname(user.getProfile().getNickname())
                .build();
    }
}
