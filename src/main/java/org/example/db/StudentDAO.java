package org.example.db;

import org.example.models.Student;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface StudentDAO {
    @SqlQuery("SELECT * FROM students")
    List<Student> getAllStudents();

    @SqlQuery("SELECT * FROM students WHERE pin = :pin")
    Student getStudentByPin(@Bind("pin") String pin);

    @SqlUpdate("INSERT INTO students (pin, first_name, last_name) VALUES (:pin, :firstName, :lastName)")
    void addStudent(@Bind("pin") String pin,
                    @Bind("firstName") String firstName,
                    @Bind("lastName") String lastName);
}
