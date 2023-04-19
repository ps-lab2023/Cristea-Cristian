package com.fitnessTracker.fitnessTrackerApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="plan_day_id", referencedColumnName = "id", nullable = false)
    private PlanDay planDay;

    @Column(nullable = false)
    private int orderNumber;

    @Column(nullable = false, length = 25)
    private String nameOfExercise;

    private LocalTime duration;

    private int noOfReps;

    @Column(length = 30)
    private String description;

    private int restBetween;

    private int noOfSets;
}
