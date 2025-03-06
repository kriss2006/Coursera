package org.example.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.security.Principal;

@AllArgsConstructor
@Getter
public class UserToken implements Principal {
    private final int id;
    private final String username;

    @Override
    public String getName() {
        return username;
    }
}
