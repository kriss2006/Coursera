package org.example.db;

import org.example.models.User;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

@RegisterBeanMapper(User.class)
public interface UserDAO {
    @SqlQuery("SELECT * FROM users")
    List<User> getAllUsers();

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    User getUserById(@Bind("id") int id);

    @SqlUpdate("INSERT INTO users (username, password_hash) VALUES (:username, :passwordHash)")
    int addUser(@Bind("username") String username,
                 @Bind("passwordHash") String passwordHash);
}
