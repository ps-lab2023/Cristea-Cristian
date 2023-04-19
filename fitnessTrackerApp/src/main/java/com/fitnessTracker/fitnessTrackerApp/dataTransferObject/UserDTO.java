package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import lombok.*;


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
    private boolean changedPassword;
    private UserRoleEnum role;
}
