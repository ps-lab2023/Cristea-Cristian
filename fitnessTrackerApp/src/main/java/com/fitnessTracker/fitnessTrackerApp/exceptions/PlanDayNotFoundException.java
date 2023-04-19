package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class PlanDayNotFoundException extends RuntimeException{
    public  PlanDayNotFoundException(String message) {
        super(message);
    }
}
