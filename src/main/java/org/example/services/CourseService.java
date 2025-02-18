package org.example.services;

import org.example.models.Course;
import org.example.db.CourseDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CourseService {
    private final CourseDAO courseDAO;

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }

    public Optional<Course> getCourseById(int id) {
        return Optional.ofNullable(courseDAO.getCourseById(id));
    }

    public int registerCourse(Course course) {
        return courseDAO.addCourse(course.getName(), course.getInstructorId(), course.getTotalTime(), course.getCredit());
    }
}