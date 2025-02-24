package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;

import org.example.health.DatabaseHealthCheck;

import org.example.db.*;
import org.example.services.*;
import org.example.resources.*;

public class CourseraApplication extends Application <CourseraConfiguration>{
    public static void main(String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public void run(CourseraConfiguration configuration, Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        StudentDAO studentDAO = jdbi.onDemand(StudentDAO.class);
        InstructorDAO instructorDAO = jdbi.onDemand(InstructorDAO.class);
        CourseDAO courseDAO = jdbi.onDemand(CourseDAO.class);
        EnrollmentDAO enrollmentDAO = jdbi.onDemand(EnrollmentDAO.class);
        ReportDAO reportDAO = jdbi.onDemand(ReportDAO.class);

        StudentService studentService = new StudentService(studentDAO);
        InstructorService instructorService = new InstructorService(instructorDAO);
        CourseService courseService = new CourseService(courseDAO);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentDAO);
        ReportService reportService = new ReportService(reportDAO);

        environment.jersey().register(new StudentResource(studentService));
        environment.jersey().register(new InstructorResource(instructorService));
        environment.jersey().register(new CourseResource(courseService));
        environment.jersey().register(new EnrollmentResource(enrollmentService));
        environment.jersey().register(new ReportResource(reportService));

        environment.healthChecks().register("database", new DatabaseHealthCheck(jdbi));
    }
}