package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentReport {
    private String studentName;
    private int totalCredit;
    private List<CourseReport> courseReportList;
}
