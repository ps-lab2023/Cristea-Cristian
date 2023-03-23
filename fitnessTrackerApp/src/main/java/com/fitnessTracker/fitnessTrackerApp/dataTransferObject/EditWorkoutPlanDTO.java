package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditWorkoutPlanDTO {
    private long id;
    private ActivityTypeEnum mainActivity;
    private LevelEnum level;
    private String description;
    private DurationDTO duration;
}
