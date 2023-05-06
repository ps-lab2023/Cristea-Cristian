package com.fitnessTracker.fitnessTrackerApp.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Slf4j
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AddUserException.class,
            UserNotFoundException.class,
            WorkoutRecordNotFoundException.class,
            PlanDayNotFoundException.class,
            WorkoutPlanNotFoundException.class,
            PlanEntryNotFoundException.class,
            UserPlanNotFoundException.class,
            IncorrectVerificationCodeException.class})
    public ResponseEntity<?> handleBadRequestException(Exception ex, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        var response = new ErrorResponse(
                guid,
                ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                request.getRequestURI(),
                request.getMethod(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
