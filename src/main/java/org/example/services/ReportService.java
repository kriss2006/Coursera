package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.db.ReportDAO;
import org.example.models.CourseReport;
import org.example.models.FlatReport;
import org.example.models.StudentReport;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ReportService {

    private final ReportDAO reportDAO;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Optional<List<StudentReport>> getStudentReports(String rawPins, Integer minCredit, String startDateStr, String endDateStr, String studentName, Integer totalCredit) {
        List<String> pins = (rawPins == null || rawPins.trim().isEmpty())
                ? Collections.emptyList()
                : Arrays.asList(rawPins.split(","));

        boolean includeAllPins = pins.isEmpty();

        LocalDate startDate = parseDate(startDateStr);
        LocalDate endDate = parseDate(endDateStr);

        List<FlatReport> flatReports = reportDAO.getFlatReports(pins, includeAllPins, minCredit, startDate, endDate);

        if (flatReports.isEmpty()) {
            return Optional.empty();
        }

        List<StudentReport> studentReports = transformFlatReports(flatReports);

        if (studentName != null && !studentName.trim().isEmpty()) {
            studentReports = studentReports.stream()
                    .filter(report -> report.getStudentName().equalsIgnoreCase(studentName))
                    .collect(Collectors.toList());
        }

        if (totalCredit != null) {
            studentReports = studentReports.stream()
                    .filter(report -> report.getTotalCredit() == totalCredit)
                    .collect(Collectors.toList());
        }

        return Optional.of(studentReports);
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + dateStr + ". Expected format: yyyy-MM-dd");
            return null;
        }
    }

    private List<StudentReport> transformFlatReports(List<FlatReport> flatReports) {
        Map<String, List<FlatReport>> groupedByStudent = flatReports.stream()
                .collect(Collectors.groupingBy(FlatReport::getStudentName));

        return groupedByStudent.entrySet().stream()
                .map(entry -> {
                    String studentName = entry.getKey();
                    List<FlatReport> studentCourses = entry.getValue();

                    int totalCredit = studentCourses.stream()
                            .map(FlatReport::getTotalCredit)
                            .findFirst()
                            .orElse(0);

                    List<CourseReport> courseReports = studentCourses.stream()
                            .map(c -> new CourseReport(c.getCourseName(), c.getTotalTime(), c.getCredit(), c.getInstructorName()))
                            .collect(Collectors.toList());

                    return new StudentReport(studentName, totalCredit, courseReports);
                })
                .collect(Collectors.toList());
    }
}
