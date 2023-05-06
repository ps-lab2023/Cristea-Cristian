package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/workoutPlan")
public class WorkoutPlanController {
    @Autowired
    private WorkoutPlanService workoutPlanService;

    @PostMapping("/addWorkoutPlan")
    public ResponseEntity<WorkoutPlanDTO> addWorkoutPlan(@RequestBody AddWorkoutPlanDTO addWorkoutPlan) {
        return ResponseEntity.ok(workoutPlanService.addWorkoutPlan(addWorkoutPlan));
    }

    @PutMapping("/updateWorkoutPlan")
    public ResponseEntity<WorkoutPlanDTO> updateWorkoutPlan(@RequestBody EditWorkoutPlanDTO editWorkoutPlan) {
        return ResponseEntity.ok(workoutPlanService.updateWorkoutPlan(editWorkoutPlan));
    }

    @GetMapping("/getWorkoutPlanDays/{workoutPlanId}")
    public ResponseEntity<List<PlanDaySummaryDTO>> getWorkoutPlanDays(@PathVariable long workoutPlanId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanDays(workoutPlanId));
    }

    @GetMapping("/getByTrainerId/{trainerId}")
    public ResponseEntity<List<WorkoutPlanSummaryDTO>> getWorkoutPlansByUserId(@PathVariable long trainerId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlansByTrainerId(trainerId));
    }

    @GetMapping("/getWorkoutPlan/{workoutPlanId}")
    public ResponseEntity<WorkoutPlanSummaryDTO> getWorkoutPlanById(@PathVariable long workoutPlanId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById((workoutPlanId)));
    }

    @GetMapping("/getNewWorkoutPlans/{userId}")
    public ResponseEntity<List<WorkoutPlanWithTrainerDTO>> getAllWorkoutPlans(@PathVariable long userId) {
        return ResponseEntity.ok(workoutPlanService.getNewWorkoutPlans(userId));
    }

    @DeleteMapping("/deleteWorkoutPlan/{workoutPlanId}")
    public ResponseEntity deleteWorkoutPlan(@PathVariable long workoutPlanId) {
        workoutPlanService.deleteWorkoutPlan(workoutPlanId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value="/getWorkoutPlanXML/{workoutPlanId}", produces = { "application/xml", "text/xml"})
    public ResponseEntity<WorkoutPlanDTO> getWorkoutPlanXML(@PathVariable long workoutPlanId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlan(workoutPlanId));
    }
}
