package org.example.services;
import org.example.models.Course;
import org.example.db.EnrollmentDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentDAO enrollmentDAO;

    public Optional<List<Course>> getCompletedCourses(String studentPin) {
        return Optional.ofNullable(enrollmentDAO.getCompletedCourses(studentPin));
    }

    public void enrollStudent(String studentPin, int courseId) {
        enrollmentDAO.enrollStudent(studentPin, courseId);
    }

    public void completeCourse(String studentPin, int courseId) {
        enrollmentDAO.completeCourse(studentPin, courseId);
    }
}