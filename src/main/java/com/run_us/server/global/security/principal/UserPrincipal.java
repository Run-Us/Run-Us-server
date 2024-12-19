package com.run_us.server.global.security.principal;

import lombok.Getter;

@Getter
public class UserPrincipal {
    private final String publicId;
    private final Integer internalId;

    public UserPrincipal(String publicId, Integer userId) {
        this.publicId = publicId;
        this.internalId = userId;
    }
}
