package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditPlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanEntryDTO;

import java.util.List;

public interface PlanEntryService {
    PlanEntryDTO getPlanEntryById(long id);
    PlanEntryDTO addPlanEntry(AddPlanEntryDTO addPlanEntry);
    void deletePlanEntry(long id);
    PlanEntryDTO updatePlanEntry(EditPlanEntryDTO editPlanDay);
}
