package com.run_us.server.domains.user.domain;

public record AuthResult(AuthResultType type, TokenPair tokenPair) {}
