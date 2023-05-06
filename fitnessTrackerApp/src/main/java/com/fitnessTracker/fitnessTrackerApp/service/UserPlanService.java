package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutPlanWithTrainerDTO;

import java.util.List;

public interface UserPlanService {
    UserPlanDTO getUserPlanById(long id);
    UserPlanDTO addUserPlan(AddUserPlanDTO addUserPlan);
    void deleteUserPlan(long userId, long planId);
    UserPlanDTO updateUserPlan(EditUserPlanDTO editUserPlan);
    List<WorkoutPlanWithTrainerDTO> getUserWorkoutPlans(long userId);
    UserPlanDTO getUserPlan(long userPlan, long planId);
    void updateCurrentDay(long userId, long planId);
}
