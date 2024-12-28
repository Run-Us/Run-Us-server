package com.run_us.server.domains.crew.controller.model.response;

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
}
