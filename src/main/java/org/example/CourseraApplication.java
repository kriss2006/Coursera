package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.jdbi3.JdbiFactory;
import org.jdbi.v3.core.Jdbi;
import org.example.resources.HelloWorldResource;
import org.example.health.TemplateHealthCheck;

public class CourseraApplication extends Application <CourseraConfiguration>{
    public static void main(String[] args) throws Exception {
        new CourseraApplication().run(args);
    }

    @Override
    public void run(CourseraConfiguration configuration, Environment environment) {
        final JdbiFactory factory = new JdbiFactory();
        final Jdbi jdbi = factory.build(environment, configuration.getDataSourceFactory(), "postgresql");
//        environment.jersey().register(new UserResource(jdbi));

        HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        environment.jersey().register(resource);

        System.out.println("Hello World!");

        TemplateHealthCheck healthCheck = new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
    }
}