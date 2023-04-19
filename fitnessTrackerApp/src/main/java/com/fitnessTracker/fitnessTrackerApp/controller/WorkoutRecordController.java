package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserWorkoutRecordsDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/workoutRecord")
public class WorkoutRecordController {
    @Autowired
    private WorkoutRecordService workoutRecordService;

    @PostMapping("/addWorkoutRecord")
    private ResponseEntity<WorkoutRecordDTO> addWorkoutRecord(@RequestBody AddWorkoutRecordDTO addWorkoutRecord)  {
        return ResponseEntity.ok(workoutRecordService.addWorkoutRecord(addWorkoutRecord));
    }

    @GetMapping("/getByUserId/{userId}?")
    private ResponseEntity<UserWorkoutRecordsDTO> getUserWorkoutRecords(@PathVariable long userId){
        return ResponseEntity.ok(workoutRecordService.getWorkoutRecordsByUser(userId));
    }

    @GetMapping("/filterByActivityType/{userId}?")
    private ResponseEntity<UserWorkoutRecordsDTO> filterWorkoutRecordsByActivityType(@PathVariable long userId, @RequestParam("activityType") ActivityTypeEnum activityType){
        return ResponseEntity.ok(workoutRecordService.filterWorkoutRecordsByActivityType(userId, activityType));
    }

    @GetMapping("/getByWorkoutId/{workoutId}")
    private ResponseEntity<WorkoutRecordDTO> getWorkoutRecordById(@PathVariable long workoutId){
        return ResponseEntity.ok(workoutRecordService.getWorkoutRecordById(workoutId));
    }

    @DeleteMapping("/deleteWorkoutRecord/{workoutId}")
    private ResponseEntity deleteWorkoutRecord(@PathVariable long workoutId){
        workoutRecordService.deleteWorkoutRecord(workoutId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/editWorkoutRecord")
    private ResponseEntity<WorkoutRecordDTO> editWorkoutRecord(@RequestBody EditWorkoutRecordDTO editWorkoutRecord) {
        return ResponseEntity.ok(workoutRecordService.updateWorkoutRecord(editWorkoutRecord));
    }
}
