package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditPlanEntryDTO {
    private long id;
    private int orderNumber;
    private String nameOfExercise;
    private DurationDTO duration;
    private int noOfReps;
    private String description;

}
