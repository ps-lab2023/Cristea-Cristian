package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class WorkoutRecordNotFoundException extends RuntimeException {
    public WorkoutRecordNotFoundException(String message) {
        super(message);
    }
}
