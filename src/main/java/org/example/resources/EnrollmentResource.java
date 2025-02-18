package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.models.Course;
import org.example.services.EnrollmentService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/enrollments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class EnrollmentResource {
    private final EnrollmentService enrollmentService;

    @GET
    @Path("/{studentPin}")
    public Response getCompletedCourses(@PathParam("studentPin") String studentPin) {
        Optional<List<Course>> completedCourses = enrollmentService.getCompletedCourses(studentPin);
        return completedCourses.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    public Response enrollStudent(@QueryParam("studentPin") String studentPin, @QueryParam("courseId") int courseId) {
        try {
            enrollmentService.enrollStudent(studentPin, courseId);
            return Response.status(Response.Status.CREATED).entity("Enrollment successful").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to enroll student: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/complete-course/")
    public Response completeCourse(@QueryParam("studentPin") String studentPin, @QueryParam("courseId") int courseId) {
        try {
            enrollmentService.completeCourse(studentPin, courseId);
            return Response.status(Response.Status.OK).entity("Course completed").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to complete course: " + e.getMessage()).build();
        }
    }
}
