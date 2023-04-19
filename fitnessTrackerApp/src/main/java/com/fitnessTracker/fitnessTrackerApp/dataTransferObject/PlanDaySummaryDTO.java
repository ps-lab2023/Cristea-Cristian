package com.fitnessTracker.fitnessTrackerApp.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PlanDaySummaryDTO {
    private long id;
    private int dayNumber;
    private int noOfEntries;
}
