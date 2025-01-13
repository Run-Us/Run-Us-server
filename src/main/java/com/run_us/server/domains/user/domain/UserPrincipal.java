package com.run_us.server.domains.user.domain;

import com.run_us.server.global.common.resolver.DomainPrincipal;

public class UserPrincipal extends DomainPrincipal {
    public UserPrincipal(String publicId, Integer internalId) {
        super(publicId, internalId);
    }
}
