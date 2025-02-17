package org.example.health;

import com.codahale.metrics.health.HealthCheck;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.Jdbi;

@AllArgsConstructor
public class DatabaseHealthCheck extends HealthCheck {
    private final Jdbi jdbi;

    @Override
    protected Result check() {
        try {
            jdbi.withHandle(handle -> handle.execute("SELECT 1"));
            return Result.healthy();
        } catch (Exception e) {
            return Result.unhealthy("Cannot connect to the database: " + e.getMessage());
        }
    }
}
