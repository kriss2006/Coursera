package org.example.auth;

import io.dropwizard.auth.Authenticator;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class JwtAuthenticator implements Authenticator<JwtContext, UserToken> {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticator.class);

    @Override
    public Optional<UserToken> authenticate(JwtContext context) {
        try {
            JwtClaims claims = context.getJwtClaims();

            int id = Integer.parseInt(claims.getSubject());
            String username = (String) claims.getClaimValue("username");

            return Optional.of(new UserToken(id, username));
        } catch (Exception e) {
            LOGGER.warn("msg=Failed to authorise user: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
