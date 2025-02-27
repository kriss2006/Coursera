package org.example.models;

import java.security.Principal;
import java.util.Objects;

public class MyUser implements Principal {
    private final String username;

    public MyUser(String username) {
        this.username = username;
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyUser myUser = (MyUser) o;
        return Objects.equals(username, myUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

