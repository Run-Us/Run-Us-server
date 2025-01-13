package com.run_us.server.global.common.resolver;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public abstract class DomainPrincipal {
    private final String publicId;
    private final Integer internalId;
}