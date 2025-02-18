package org.example.resources;

import lombok.RequiredArgsConstructor;
import org.example.models.Course;
import org.example.services.CourseService;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class CourseResource {
    private final CourseService courseService;

    @GET
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") int id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerInstructor(Course course) {
        int courseId = courseService.registerCourse(course);
        return Response.status(Response.Status.CREATED).entity(courseId).build();
    }
}
