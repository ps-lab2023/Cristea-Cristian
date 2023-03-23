package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class WorkoutRecordDTO {
    private long id;
    private long userId;
    private ActivityTypeEnum activityType;
    private LocalDateTime date;
    private double distance;
    private LocalTime duration;
    private int calories;
}
