package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanDayRepository extends JpaRepository<PlanDay, Long> {
}
