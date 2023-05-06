package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPlanRepository extends JpaRepository<UserPlan, Long> {
    UserPlan findByUserAndPlan(User user, WorkoutPlan plan);
}
