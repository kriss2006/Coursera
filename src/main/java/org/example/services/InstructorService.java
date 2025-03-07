package org.example.services;

import org.example.models.Instructor;
import org.example.db.InstructorDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class InstructorService {
    private final InstructorDAO instructorDAO;

    public List<Instructor> getAllInstructors() {
        return instructorDAO.getAllInstructors();
    }

    public Optional<Instructor> getInstructorById(int id) {
        return Optional.ofNullable(instructorDAO.getInstructorById(id));
    }

    public int addInstructor(Instructor instructor) {
        return instructorDAO.addInstructor(instructor.getFirstName(), instructor.getLastName());
    }
}