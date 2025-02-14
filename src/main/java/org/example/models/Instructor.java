package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    private int id;
    private String firstName;
    private String lastName;
    private LocalDateTime timeCreated;
}
