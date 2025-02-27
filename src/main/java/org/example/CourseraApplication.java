package org.example;

import com.google.common.collect.ImmutableMap;
import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.PolymorphicAuthDynamicFeature;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.example.auth.JwtFilterUtil;
import org.example.auth.UserToken;
import org.jdbi.v3.core.Jdbi;

import org.example.health.DatabaseHealthCheck;

import org.example.db.*;
import org.example.services.*;
import org.example.resources.*;
import org.jose4j.jwt.consumer.JwtContext;
import io.dropwizard.auth.PolymorphicAuthValueFactoryProvider;
import com.google.common.collect.ImmutableSet;

public class CourseraApplication extends Application<CourseraConfiguration> {
    public static void main(String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public void run(CourseraConfiguration configuration, Environment environment) {
        registerResources(environment, configuration);
        registerAuthFilters(environment);
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

    private void registerAuthFilters(Environment environment) {
        JwtFilterUtil jwtFilterUtil = new JwtFilterUtil();
        final AuthFilter<JwtContext, UserToken> jwtFilter = jwtFilterUtil.buildJwtAuthFilter();

        final PolymorphicAuthDynamicFeature<UserToken> feature = new PolymorphicAuthDynamicFeature<>(
                ImmutableMap.of(UserToken.class, jwtFilter));

        final PolymorphicAuthValueFactoryProvider.Binder<UserToken> binder = new PolymorphicAuthValueFactoryProvider.Binder<>(
                ImmutableSet.of(UserToken.class));

        environment.jersey().register(feature);
        environment.jersey().register(binder);
    }
}