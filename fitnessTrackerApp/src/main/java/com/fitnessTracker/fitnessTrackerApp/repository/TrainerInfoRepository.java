package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.PlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerInfoRepository extends JpaRepository<PlanEntry, Long> {
}
