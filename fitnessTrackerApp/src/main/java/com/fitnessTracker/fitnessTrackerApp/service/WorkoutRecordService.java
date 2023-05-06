package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.ReportType;

import java.util.List;

public interface WorkoutRecordService {
    WorkoutRecordDTO getWorkoutRecordById(long id);
    List<WorkoutRecordDTO> getWorkoutRecordsByUserId(long userId);
    WorkoutRecordDTO addWorkoutRecord(AddWorkoutRecordDTO addWorkoutRecord);
    void deleteWorkoutRecord(long id);
    WorkoutRecordDTO updateWorkoutRecord(EditWorkoutRecordDTO newWorkoutRecord);
    UserWorkoutRecordsDTO getWorkoutRecordsByUser(long userId);
    UserWorkoutRecordsDTO filterWorkoutRecordsByActivityType(long userId, ActivityTypeEnum activityType);
    List<ChartDataDTO> getChartData(long userId, int month, int year, ReportType reportType);
}
