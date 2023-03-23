package com.fitnessTracker.fitnessTrackerApp.model;

import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(name="activity_type", nullable = false)
    private ActivityTypeEnum activityType;

    private LocalDateTime date;

    private double distance;

    @Column(nullable = false)
    private LocalTime duration;

    @Column(nullable = false)
    private int calories;
}
