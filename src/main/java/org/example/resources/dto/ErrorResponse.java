package org.example.resources.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse {

    public ErrorResponse(String error) {
        this.error = error;
    }

    @JsonProperty("error")
    public String error;
}