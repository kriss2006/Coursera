package org.example.resources;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.example.auth.JwtUtils;
import org.example.models.User;
import org.example.resources.dto.LoginRequest;
import org.example.services.UserService;
import org.jose4j.lang.JoseException;
import org.example.resources.dto.LoginResponse;

import java.util.Optional;

@Path("auth")
@Produces(APPLICATION_JSON)
@RequiredArgsConstructor
public class LoginResource {
    private final UserService userService;

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest) {
        Optional<User> userOptional = userService.getUserByUsername(loginRequest.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (user.getPasswordHash().equals(loginRequest.getPassword())) {
                try {
                    String token = JwtUtils.generateToken(user.getUsername());
                    return Response.ok(new LoginResponse(token)).build();
                } catch (JoseException e) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Token generation failed").build();
                }
            }
        }

        return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid credentials").build();
    }
}