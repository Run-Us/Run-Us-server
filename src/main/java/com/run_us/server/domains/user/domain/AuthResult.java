package com.run_us.server.domains.user.domain;

import lombok.Getter;

@Getter
public record AuthResult (AuthResultType type, TokenPair tokenPair) {

}
