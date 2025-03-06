package org.example.auth;

import io.dropwizard.auth.Authenticator;
import org.jose4j.jwt.JwtClaims;

import java.util.Optional;

public class JwtAuthenticator implements Authenticator<String, UserToken> {
    @Override
    public Optional<UserToken> authenticate(String token) {
        try {
            JwtClaims claims = JwtUtils.parseToken(token);
            int id = Integer.parseInt(claims.getSubject());
            String username = claims.getStringClaimValue("username");

            return Optional.of(new UserToken(id, username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
