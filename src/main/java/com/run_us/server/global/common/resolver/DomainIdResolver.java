package com.run_us.server.global.common.resolver;

public interface DomainIdResolver<T extends DomainPrincipal> {
    T resolve(String publicId);
    T resolve(Integer internalId);
}