package org.example;

import io.dropwizard.auth.*;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.example.auth.JwtAuthenticator;
import org.example.auth.JwtUtils;
import org.example.auth.UserToken;
import org.jdbi.v3.core.Jdbi;

import org.example.health.DatabaseHealthCheck;

import org.example.db.*;
import org.example.services.*;
import org.example.resources.*;

import java.util.EnumSet;

public class CourseraApplication extends Application<CourseraConfiguration> {
    public static void main(String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public void run(CourseraConfiguration configuration, Environment environment) {
        registerResources(environment, configuration);
        JwtUtils.initialize(configuration.getJwtSecret());

        registerAuth(environment);
        configureCors(environment);
    }

    private void registerResources(Environment environment, CourseraConfiguration configuration) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");

        StudentDAO studentDAO = jdbi.onDemand(StudentDAO.class);
        InstructorDAO instructorDAO = jdbi.onDemand(InstructorDAO.class);
        CourseDAO courseDAO = jdbi.onDemand(CourseDAO.class);
        EnrollmentDAO enrollmentDAO = jdbi.onDemand(EnrollmentDAO.class);
        ReportDAO reportDAO = jdbi.onDemand(ReportDAO.class);
        UserDAO userDAO = jdbi.onDemand(UserDAO.class);

        StudentService studentService = new StudentService(studentDAO);
        InstructorService instructorService = new InstructorService(instructorDAO);
        CourseService courseService = new CourseService(courseDAO);
        EnrollmentService enrollmentService = new EnrollmentService(enrollmentDAO);
        ReportService reportService = new ReportService(reportDAO);
        UserService userService = new UserService(userDAO);

        environment.healthChecks().register("database", new DatabaseHealthCheck(jdbi));
        environment.jersey().register(new StudentResource(studentService));
        environment.jersey().register(new InstructorResource(instructorService));
        environment.jersey().register(new CourseResource(courseService));
        environment.jersey().register(new EnrollmentResource(enrollmentService));
        environment.jersey().register(new ReportResource(reportService));
        environment.jersey().register(new UserResource(userService));
        environment.jersey().register(new LoginResource(userService));
    }

    private void registerAuth(Environment environment) {
        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<UserToken>()
                        .setAuthenticator(new JwtAuthenticator())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(UserToken.class));
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors = environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "http://localhost:3000");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    }
}