package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class UserPlanNotFoundException extends RuntimeException{
    public UserPlanNotFoundException(String message) {
        super(message);
    }
}
