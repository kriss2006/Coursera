package org.example.auth;

import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.jwt.consumer.JwtContext;
import org.jose4j.keys.HmacKey;
import com.github.toastshaman.dropwizard.auth.jwt.JwtAuthFilter;
import javax.ws.rs.core.SecurityContext;

import io.dropwizard.auth.AuthFilter;

public class JwtFilterUtil {
    public AuthFilter<JwtContext, UserToken> buildJwtAuthFilter() {
        // These requirements would be tightened up for production use
        final JwtConsumer consumer = new JwtConsumerBuilder().setAllowedClockSkewInSeconds(300).setRequireSubject()
                .setVerificationKey(new HmacKey(Secrets.JWT_SECRET_KEY)).build();

        return new JwtAuthFilter.Builder<UserToken>().setJwtConsumer(consumer).setRealm("realm").setPrefix("Bearer")
                .setAuthenticator(new JwtAuthenticator()).buildAuthFilter();
    }
}