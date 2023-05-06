package com.fitnessTracker.fitnessTrackerApp.exceptions;

public class IncorrectVerificationCodeException extends RuntimeException{
    public IncorrectVerificationCodeException(String message) {
        super(message);
    }
}
