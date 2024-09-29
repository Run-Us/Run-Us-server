package com.run_us.server.domains.user.service.verifier;

import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenVerifier {
    DecodedJWT verifyToken(String token, String nonce);
}