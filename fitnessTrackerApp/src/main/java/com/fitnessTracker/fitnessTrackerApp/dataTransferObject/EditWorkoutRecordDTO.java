package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditWorkoutRecordDTO {
    private long id;
    private double distance;
    private DurationDTO duration;
    private int calories;
    private ActivityTypeEnum activityType;
}
