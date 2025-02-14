package org.example.db;

import org.example.models.Instructor;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface InstructorDAO {

    @SqlQuery("SELECT * FROM instructors")
    List<Instructor> getAllInstructors();

    @SqlQuery("SELECT * FROM instructors WHERE id = :id")
    Instructor getInstructorById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO instructors (first_name, last_name) VALUES (:firstName, :lastName)")
    @GetGeneratedKeys
    int addInstructor(@Bind("firstName") String firstName, @Bind("lastName") String lastName);
}
