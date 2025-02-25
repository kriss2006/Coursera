package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.models.User;
import org.example.services.UserService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {
    private final UserService userService;

    @GET
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerUser(User user) {
        int userId = userService.addUser(user);
        return Response.status(Response.Status.CREATED).entity(userId).build();
    }
}
