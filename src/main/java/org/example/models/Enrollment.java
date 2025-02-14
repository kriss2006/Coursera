package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    private String studentPin;
    private int courseId;
    private LocalDate completionDate;
}
