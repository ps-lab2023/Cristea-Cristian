package com.fitnessTracker.fitnessTrackerApp.model;

import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="trainer_id", referencedColumnName = "id", nullable = false)
    private User trainer;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private ActivityTypeEnum mainActivity;

    private String goal;

    @Column(nullable = false)
    private LevelEnum level;

    @Column(length = 100)
    private String description;

    @OneToMany(mappedBy = "plan", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<PlanDay> planDays;
}
