package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.auth.UserToken;
import org.example.models.Instructor;
import org.example.resources.dto.PostResponse;
import org.example.services.InstructorService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.dropwizard.auth.Auth;

import java.util.List;
import java.util.Optional;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class InstructorResource {
    private final InstructorService instructorService;

    @GET
    public List<Instructor> getAllInstructors(@Auth UserToken userToken) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        return instructorService.getAllInstructors();
    }

    @GET
    @Path("/{id}")
    public Response getInstructorById(@Auth UserToken userToken, @PathParam("id") int id) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        Optional<Instructor> instructor = instructorService.getInstructorById(id);
        return instructor.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerInstructor(@Auth UserToken userToken, Instructor instructor) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        int instructorId = instructorService.addInstructor(instructor);
        return Response.status(Response.Status.CREATED).entity(new PostResponse(instructorId)).build();
    }
}
