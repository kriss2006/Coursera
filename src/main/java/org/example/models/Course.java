package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.jdbi.v3.core.mapper.reflect.ColumnName;
import org.jdbi.v3.core.mapper.reflect.JdbiConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Course {
    private int id;
    private String name;
    private int instructorId;
    private int totalTime;
    private int credit;
    private LocalDateTime timeCreated;

    @JdbiConstructor
    @SuppressWarnings("unused")
    public Course(
            @ColumnName("id") int id,
            @ColumnName("name") String name,
            @ColumnName("instructor_id") int instructorId,
            @ColumnName("total_time") int totalTime,
            @ColumnName("credit") int credit,
            @ColumnName("time_created") LocalDateTime timeCreated) {
        this.id = id;
        this.name = name;
        this.instructorId = instructorId;
        this.totalTime = totalTime;
        this.credit = credit;
        this.timeCreated = timeCreated;
    }
}
