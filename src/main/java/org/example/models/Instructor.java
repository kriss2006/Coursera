package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Instructor {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime timeCreated;

    @JdbiConstructor
    @SuppressWarnings("unused")
    public Instructor(
            @ColumnName("id") int id,
            @ColumnName("first_name") String firstName,
            @ColumnName("last_name") String lastName,
            @ColumnName("time_created") LocalDateTime timeCreated) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.timeCreated = timeCreated;
    }
}
