package com.fitnessTracker.fitnessTrackerApp.model;

import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, length = 25)
    private String username;

    @Column(nullable = false, length = 45)
    private String name;

    @Column(nullable = false, length = 45)
    private String password;

    @Column(nullable = false, length = 45)
    @Email(message = "invalid email")
    private String email;

    @Column(nullable = false)
    private GenderEnum gender;

    private int height;

    private int weight;

    @Column(nullable = false)
    private UserRoleEnum role;

    private boolean changedPassword;

    private boolean isLoggedIn;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<WorkoutRecord> workoutRecords;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<UserPlan> userPlans;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<WorkoutPlan> workoutPlans;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private VerificationCode verificationCode;
}
