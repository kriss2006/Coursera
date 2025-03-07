package org.example.resources;

import io.dropwizard.auth.Auth;

import org.example.auth.UserToken;
import org.example.models.Student;
import org.example.services.StudentService;
import lombok.RequiredArgsConstructor;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class StudentResource {
    private final StudentService studentService;

    @GET
    public List<Student> getAllStudents(@Auth UserToken userToken) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        return studentService.getAllStudents();
    }

    @GET
    @Path("/{pin}")
    public Response getStudentByPin(@Auth UserToken userToken,
                                    @PathParam("pin") String pin) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        Optional<Student> student = studentService.getStudentByPin(pin);
        return student.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @POST
    public Response registerStudent(@Auth UserToken userToken, Student student) {
        System.out.println("Authenticated user: " + userToken.getUsername());

        studentService.registerStudent(student);
        return Response.status(Response.Status.CREATED).build();
    }
}
