package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MonthSummaryDTO {
    private int month;
    private int year;
    private double totalDistance;
    private double totalTime;
    private int numberOfCalories;
    private int numberOfTimes;
    private int calories;
    private List<WorkoutRecordDTO> workoutRecords;
}
