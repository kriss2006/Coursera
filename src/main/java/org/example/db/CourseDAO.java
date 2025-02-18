package org.example.db;

import org.example.models.Course;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterConstructorMapper(Course.class)
public interface CourseDAO {
    @SqlQuery("SELECT * FROM courses")
    List<Course> getAllCourses();

    @SqlQuery("SELECT * FROM courses WHERE id = :id")
    Course getCourseById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO courses (name, instructor_id, total_time, credit) VALUES (:name, :instructorId, :totalTime, :credit)")
    @GetGeneratedKeys
    int addCourse(@Bind("name") String name,
                  @Bind("instructorId") int instructorId,
                  @Bind("totalTime") int totalTime,
                  @Bind("credit") int credit);
}
