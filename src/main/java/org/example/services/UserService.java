package org.example.services;

import org.example.models.User;
import org.example.db.UserDAO;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserDAO userDAO;

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public Optional<User> getUserById(int id) {
        return Optional.ofNullable(userDAO.getUserById(id));
    }

    public Optional<User> getUserByUsername(String username) {
        return Optional.ofNullable(userDAO.getUserByUsername(username));
    }

    public int addUser(User user) {
        return userDAO.addUser(user.getUsername(), user.getPasswordHash());
    }
}