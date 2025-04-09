package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.auth.UserToken;
import org.example.models.Course;
import org.example.resources.dto.EnrollmentRequest;
import org.example.services.EnrollmentService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.dropwizard.auth.Auth;

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
    public Response getCompletedCourses(@Auth UserToken userToken, @PathParam("studentPin") String studentPin) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        Optional<List<Course>> completedCourses = enrollmentService.getCompletedCourses(studentPin);
        return completedCourses.map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
    }

    @POST
    public Response enrollStudent(@Auth UserToken userToken, EnrollmentRequest enrollmentRequest) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        try {
            enrollmentService.enrollStudent(enrollmentRequest.getStudentPin(), enrollmentRequest.getCourseId());
            return Response.status(Response.Status.CREATED).entity("Enrollment successful").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to enroll student: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("/complete-course/")
    public Response completeCourse(@Auth UserToken userToken, @QueryParam("studentPin") String studentPin, @QueryParam("courseId") int courseId) {
        System.out.println("Authenticated user: " + userToken.getUsername());
        try {
            enrollmentService.completeCourse(studentPin, courseId);
            return Response.status(Response.Status.OK).entity("Course completed").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Failed to complete course: " + e.getMessage()).build();
        }
    }
}
