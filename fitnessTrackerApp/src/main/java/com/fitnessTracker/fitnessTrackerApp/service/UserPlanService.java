package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;

public interface UserPlanService {
    UserPlanDTO getUserPlanById(long id);
    UserPlanDTO addUserPlan(AddUserPlanDTO addUserPlan);
    void deleteUserPlan(long id);
    UserPlanDTO updateUserPlan(EditUserPlanDTO editUserPlan);
}
