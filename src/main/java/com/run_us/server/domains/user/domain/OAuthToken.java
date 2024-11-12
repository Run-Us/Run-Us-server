package com.run_us.server.domains.user.domain;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.Getter;

@Getter
public class OAuthToken {
    private final String token;
    private final String sub;
    private final String nonce;
    private final SocialProvider provider;

    private OAuthToken(String token, String sub, String nonce, SocialProvider provider) {
        this.token = token;
        this.sub = sub;
        this.nonce = nonce;
        this.provider = provider;
    }

    public static OAuthToken from(String token, SocialProvider provider) throws JWTDecodeException {
        DecodedJWT jwt = JWT.decode(token);

        String sub = jwt.getSubject();
        String nonce = jwt.getClaim("nonce").asString();

        if (sub == null || nonce == null) {
            throw new IllegalArgumentException("Token does not contain essential claims");
        }

        return new OAuthToken(token, sub, nonce, provider);
    }
}
