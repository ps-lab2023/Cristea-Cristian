package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutPlanWithTrainerDTO;
import com.fitnessTracker.fitnessTrackerApp.service.UserPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/userPlan")
public class UserPlanController {
    @Autowired
    private UserPlanService userPlanService;

    @PostMapping("/addUserPlan")
    public ResponseEntity<UserPlanDTO> addUserPlan(@RequestBody AddUserPlanDTO addUserPlan) {
        return ResponseEntity.ok(userPlanService.addUserPlan(addUserPlan));
    }

    @GetMapping("/getUserWorkoutPlans/{userId}")
    public ResponseEntity<List<WorkoutPlanWithTrainerDTO>> getUserWorkoutPlans(@PathVariable long userId) {
        return ResponseEntity.ok(userPlanService.getUserWorkoutPlans(userId));
    }

    @GetMapping("/getUserPlan")
    public ResponseEntity<UserPlanDTO> getUserPlan(@RequestParam("userId") long userId, @RequestParam("planId") long planId) {
        return ResponseEntity.ok(userPlanService.getUserPlan(userId, planId));
    }

    @DeleteMapping("/deleteUserPlan")
    public ResponseEntity deleteUserPlan(@RequestParam("userId") long userId, @RequestParam("planId") long planId) {
        userPlanService.deleteUserPlan(userId, planId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateCurrentDay")
    public ResponseEntity updateCurrentDay(@RequestParam("userId") long userId, @RequestParam("planId") long planId) {
        userPlanService.updateCurrentDay(userId, planId);
        return ResponseEntity.ok().build();
    }
}
