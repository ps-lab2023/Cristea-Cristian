package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutRecord;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDTO {
    private long id;
    private String username;
    private String name;
    private String password;
    private String email;
    private GenderEnum gender;
    private int height;
    private int weight;
    private UserRoleEnum role;
}
