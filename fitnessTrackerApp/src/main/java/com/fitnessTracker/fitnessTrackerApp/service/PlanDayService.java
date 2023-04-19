package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanEntryDTO;

import java.util.List;

public interface PlanDayService {
    PlanDayDTO getPlanDayById(long id);
    PlanDayDTO addPlanDay(AddPlanDayDTO addPlanDay);
    void deletePlanDay(long id);
    PlanDayDTO updatePlanDay(EditPlanDayDTO editPlanDay);
    List<PlanEntryDTO> getPlanDayEntries(long id);
}
