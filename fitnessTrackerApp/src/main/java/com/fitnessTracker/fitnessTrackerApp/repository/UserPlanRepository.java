package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
}
