package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {
}
