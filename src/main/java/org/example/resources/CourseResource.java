package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.auth.UserToken;
import org.example.models.Course;
import org.example.resources.dto.PostResponse;
import org.example.services.CourseService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.dropwizard.auth.Auth;

import java.util.List;
import java.util.Optional;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CourseResource {
    private final CourseService courseService;

    @GET
    public List<Course> getAllCourses(@Auth UserToken userToken) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        return courseService.getAllCourses();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@Auth UserToken userToken, @PathParam("id") int id) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        Optional<Course> course = courseService.getCourseById(id);
        return course.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerInstructor(@Auth UserToken userToken, Course course) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        int courseId = courseService.addCourse(course);
        return Response.status(Response.Status.CREATED).entity(new PostResponse(courseId)).build();
    }
}
