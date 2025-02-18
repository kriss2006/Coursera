package org.example.services;

import org.example.models.Student;
import org.example.db.StudentDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StudentService {
    private final StudentDAO studentDAO;

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    public Optional<Student> getStudentByPin(String pin) {
        return Optional.ofNullable(studentDAO.getStudentByPin(pin));
    }

    public void registerStudent(Student student) {
        studentDAO.addStudent(student.getPin(), student.getFirstName(), student.getLastName());
    }
}