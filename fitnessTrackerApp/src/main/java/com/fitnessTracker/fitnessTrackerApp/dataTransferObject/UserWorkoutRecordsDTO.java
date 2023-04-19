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
public class UserWorkoutRecordsDTO {
    private double duration;
    private int numberOfSessions;
    private int calories;
    private List<MonthSummaryDTO> monthSummaries;
}
