package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.PlanEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanEntryRepository extends JpaRepository<PlanEntry, Long> {
}
