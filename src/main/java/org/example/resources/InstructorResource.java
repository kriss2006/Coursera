package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.models.Instructor;
import org.example.services.InstructorService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/instructors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class InstructorResource {
    private final InstructorService instructorService;

    @GET
    public List<Instructor> getAllInstructors() {
        return instructorService.getAllInstructors();
    }

    @GET
    @Path("/{id}")
    public Response getInstructorById(@PathParam("id") int id) {
        Optional<Instructor> instructor = instructorService.getInstructorById(id);
        return instructor.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerInstructor(Instructor instructor) {
        int instructorId = instructorService.registerInstructor(instructor);
        return Response.status(Response.Status.CREATED).entity(instructorId).build();
    }
}
