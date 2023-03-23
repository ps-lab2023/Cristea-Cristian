package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserPlanDTO {
    private long id;
    private long userId;
    private long planId;
    private int currentDay;
}
