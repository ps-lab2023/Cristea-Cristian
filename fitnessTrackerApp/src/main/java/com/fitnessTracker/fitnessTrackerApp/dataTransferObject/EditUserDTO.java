package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditUserDTO {
    private long id;
    private String name;
    private String password;
    private int height;
    private int weight;
}
