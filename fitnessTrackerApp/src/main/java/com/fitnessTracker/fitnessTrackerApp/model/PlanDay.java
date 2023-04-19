package com.fitnessTracker.fitnessTrackerApp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="plan_id", referencedColumnName = "id", nullable = false)
    private WorkoutPlan plan;

    @Column(nullable = false)
    private int dayNumber;

    @OneToMany(mappedBy = "planDay", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<PlanEntry> planEntries;
}
