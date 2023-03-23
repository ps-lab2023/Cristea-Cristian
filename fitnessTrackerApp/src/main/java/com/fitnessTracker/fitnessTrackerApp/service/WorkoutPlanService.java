package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutPlanDTO;

import java.util.List;

public interface WorkoutPlanService {
    WorkoutPlanDTO addWorkoutPlan(AddWorkoutPlanDTO newPlan);
    WorkoutPlanDTO getWorkoutPlanById(long id);
    List<WorkoutPlanDTO> getWorkoutPlansByTrainerId(long trainerId);
    void deleteWorkoutPlan(long id);
    WorkoutPlanDTO updateWorkoutPlan(EditWorkoutPlanDTO editWorkoutPlanDTO);
}
