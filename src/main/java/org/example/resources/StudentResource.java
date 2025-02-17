package org.example.resources;

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

    @POST
    public Response registerStudent(Student student) {
        studentService.registerStudent(student);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Path("/{pin}")
    public Response getStudentByPin(@PathParam("pin") String pin) {
        Optional<Student> student = studentService.getStudentByPin(pin);
        return student.map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
}
