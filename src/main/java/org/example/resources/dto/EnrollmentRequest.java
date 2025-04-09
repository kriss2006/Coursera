package org.example.resources.dto;

import lombok.Data;

@Data
public class EnrollmentRequest {
    private String studentPin;
    private int courseId;
}