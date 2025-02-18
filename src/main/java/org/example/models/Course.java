package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    public Course(
            @JsonProperty("name") String name,
            @JsonProperty("instructorId") int instructorId,
            @JsonProperty("totalTime") int totalTime,
            @JsonProperty("credit") int credit) {
        this.name = name;
        this.instructorId = instructorId;
        this.totalTime = totalTime;
        this.credit = credit;
        this.timeCreated = LocalDateTime.now();
    }
}
