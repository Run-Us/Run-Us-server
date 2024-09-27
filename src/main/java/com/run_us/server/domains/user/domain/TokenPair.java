package com.run_us.server.domains.user.domain;

import lombok.Getter;

@Getter
public record TokenPair(String accessToken, String refreshToken) {

}
