package org.example.db;

import org.example.models.Enrollment;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface EnrollmentDAO {

    @SqlUpdate("INSERT INTO students_courses_xref (student_pin, course_id) VALUES (:studentPin, :courseId)")
    void enrollStudent(@Bind("studentPin") String studentPin, @Bind("courseId") int courseId);

    @SqlUpdate("UPDATE students_courses_xref SET completion_date = CURRENT_DATE WHERE student_pin = :studentPin AND course_id = :courseId")
    void completeCourse(@Bind("studentPin") String studentPin, @Bind("courseId") int courseId);

    @SqlQuery("SELECT * FROM students_courses_xref WHERE student_pin = :studentPin AND completion_date IS NOT NULL")
    List<Enrollment> getCompletedCourses(@Bind("studentPin") String studentPin);
}
