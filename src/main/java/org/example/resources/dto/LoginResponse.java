package org.example.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    public LoginResponse(String token) {
        this.token = token;
    }

    @JsonProperty("token")
    public String token;

}