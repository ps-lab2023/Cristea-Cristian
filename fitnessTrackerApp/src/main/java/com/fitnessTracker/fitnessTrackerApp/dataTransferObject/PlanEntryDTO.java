package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanEntryDTO {
    private long id;
    private long planDayId;
    private int orderNumber;
    private String nameOfExercise;
    private LocalTime duration;
    private int noOfReps;
    private String description;
    private int restBetween;
    private int noOfSets;
}
