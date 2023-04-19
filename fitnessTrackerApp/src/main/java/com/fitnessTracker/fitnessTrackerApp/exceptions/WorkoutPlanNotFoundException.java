package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class WorkoutPlanNotFoundException extends RuntimeException{
    public WorkoutPlanNotFoundException(String message) {
        super(message);
    }
}
