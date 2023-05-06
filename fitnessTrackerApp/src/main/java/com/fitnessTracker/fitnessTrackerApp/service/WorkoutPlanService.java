package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;

import java.util.List;

public interface WorkoutPlanService {
    WorkoutPlanDTO addWorkoutPlan(AddWorkoutPlanDTO newPlan);
    WorkoutPlanSummaryDTO getWorkoutPlanById(long id);
    List<WorkoutPlanSummaryDTO> getWorkoutPlansByTrainerId(long trainerId);
    void deleteWorkoutPlan(long id);
    WorkoutPlanDTO updateWorkoutPlan(EditWorkoutPlanDTO editWorkoutPlanDTO);
    List<PlanDaySummaryDTO> getWorkoutPlanDays(long id);
    List<WorkoutPlanWithTrainerDTO> getNewWorkoutPlans(long id);
    WorkoutPlanDTO getWorkoutPlan(long id);
}
