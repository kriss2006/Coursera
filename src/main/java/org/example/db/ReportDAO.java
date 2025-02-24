package org.example.db;

import org.example.models.FlatReport;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindList;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.time.LocalDate;
import java.util.List;

@RegisterBeanMapper(FlatReport.class)
public interface ReportDAO {

    @SqlQuery(
            "SELECT " +
            "    s.pin AS student_pin, " +
            "    CONCAT(s.first_name, ' ', s.last_name) AS student_name, " +
            "    COALESCE(SUM(c.credit) OVER (PARTITION BY s.pin), 0) AS total_credit, " +
            "    c.name AS course_name, " +
            "    c.total_time, " +
            "    c.credit, " +
            "    CONCAT(i.first_name, ' ', i.last_name) AS instructor_name " +
            "FROM students s " +
            "LEFT JOIN students_courses_xref sc ON s.pin = sc.student_pin " +
            "LEFT JOIN courses c ON sc.course_id = c.id " +
            "LEFT JOIN instructors i ON c.instructor_id = i.id " +
            "WHERE (:includeAllPins = TRUE OR s.pin IN (<pins>)) " +
            "AND (:minCredit IS NULL OR c.credit >= :minCredit) " +
            "AND (:startDate IS NULL OR sc.completion_date >= :startDate) " +
            "AND (:endDate IS NULL OR sc.completion_date <= :endDate) " +
            "ORDER BY student_name, course_name;"
    )
    List<FlatReport> getFlatReports(
            @BindList(value = "pins", onEmpty = BindList.EmptyHandling.NULL_STRING) List<String> pins,
            @Bind("includeAllPins") boolean includeAllPins,
            @Bind("minCredit") Integer minCredit,
            @Bind("startDate") LocalDate startDate,
            @Bind("endDate") LocalDate endDate
    );
}
