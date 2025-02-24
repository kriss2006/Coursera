package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseReport {
    private String courseName;
    private String totalTime;
    private int credit;
    private String instructorName;
}
