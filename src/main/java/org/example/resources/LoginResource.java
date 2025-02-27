package org.example.resources;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jose4j.jws.AlgorithmIdentifiers.HMAC_SHA256;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.example.models.User;
import org.example.resources.dto.LoginRequest;
import org.example.services.UserService;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import org.example.auth.Secrets;
import org.example.resources.dto.LoginResponse;

import io.dropwizard.auth.Auth;
import io.dropwizard.auth.PrincipalImpl;
import io.dropwizard.jersey.caching.CacheControl;

import java.util.Optional;

/**
 * Exchanges login information for a JWT token.
 *
 * @author Hendrik van Huyssteen
 * @since 09 Aug 2017
 */

@Path("auth")
@Produces(APPLICATION_JSON)
@RequiredArgsConstructor
public class LoginResource {
    private final UserService userService;

    @GET
    @Path("/login")
//    @CacheControl(noCache = true, noStore = true, mustRevalidate = true, maxAge = 0)
//    public final LoginResponse doLogin(@Auth PrincipalImpl user) throws JoseException {
//        return new LoginResponse(buildToken(user).getCompactSerialization());
//    }
    public Response doLogin(LoginRequest loginRequest) throws JoseException {
        Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPasswordHash().equals(loginRequest.getPassword())) {
                PrincipalImpl principal = new PrincipalImpl(user.getUsername());
                String token = buildToken(principal).getCompactSerialization();
                return Response.ok(new LoginResponse(token)).build();
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
    }

    private JsonWebSignature buildToken(PrincipalImpl user) {
        final JwtClaims claims = new JwtClaims();
        claims.setSubject("1");
        claims.setStringClaim("username", user.getName());
        claims.setIssuedAtToNow();
        claims.setGeneratedJwtId();

        final JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(HMAC_SHA256);
        jws.setKey(new HmacKey(Secrets.JWT_SECRET_KEY));
        return jws;
    }
}