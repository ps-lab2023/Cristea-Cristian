package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class PlanEntryNotFoundException extends RuntimeException{
    public  PlanEntryNotFoundException(String message) {
        super(message);
    }
}
