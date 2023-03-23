package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutRecordDTO;

import java.util.List;

public interface WorkoutRecordService {
    WorkoutRecordDTO getWorkoutRecordById(long id);
    List<WorkoutRecordDTO> getWorkoutRecordsByUserId(long userId);
    WorkoutRecordDTO addWorkoutRecord(AddWorkoutRecordDTO addWorkoutRecord);
    void deleteWorkoutRecord(long id);
    WorkoutRecordDTO updateWorkoutRecord(EditWorkoutRecordDTO newWorkoutRecord);
}