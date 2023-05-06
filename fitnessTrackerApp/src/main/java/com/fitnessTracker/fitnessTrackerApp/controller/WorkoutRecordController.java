package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.ReportType;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/workoutRecord")
public class WorkoutRecordController {
    @Autowired
    private WorkoutRecordService workoutRecordService;

    @PostMapping("/addWorkoutRecord")
    public ResponseEntity<WorkoutRecordDTO> addWorkoutRecord(@RequestBody AddWorkoutRecordDTO addWorkoutRecord)  {
        return ResponseEntity.ok(workoutRecordService.addWorkoutRecord(addWorkoutRecord));
    }

    @GetMapping("/getByUserId/{userId}?")
    public ResponseEntity<UserWorkoutRecordsDTO> getUserWorkoutRecords(@PathVariable long userId){
        return ResponseEntity.ok(workoutRecordService.getWorkoutRecordsByUser(userId));
    }

    @GetMapping("/filterByActivityType/{userId}?")
    public ResponseEntity<UserWorkoutRecordsDTO> filterWorkoutRecordsByActivityType(@PathVariable long userId, @RequestParam("activityType") ActivityTypeEnum activityType){
        return ResponseEntity.ok(workoutRecordService.filterWorkoutRecordsByActivityType(userId, activityType));
    }

    @GetMapping("/getByWorkoutId/{workoutId}")
    public ResponseEntity<WorkoutRecordDTO> getWorkoutRecordById(@PathVariable long workoutId){
        return ResponseEntity.ok(workoutRecordService.getWorkoutRecordById(workoutId));
    }

    @DeleteMapping("/deleteWorkoutRecord/{workoutId}")
    public ResponseEntity deleteWorkoutRecord(@PathVariable long workoutId){
        workoutRecordService.deleteWorkoutRecord(workoutId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/editWorkoutRecord")
    public ResponseEntity<WorkoutRecordDTO> editWorkoutRecord(@RequestBody EditWorkoutRecordDTO editWorkoutRecord) {
        return ResponseEntity.ok(workoutRecordService.updateWorkoutRecord(editWorkoutRecord));
    }

    @GetMapping("/chartData")
    public  ResponseEntity<List<ChartDataDTO>> getChartData(@RequestParam("userId") long userId, @RequestParam("month") int month, @RequestParam("year") int year, @RequestParam("reportType") ReportType reportType){
        return ResponseEntity.ok(workoutRecordService.getChartData(userId, month, year, reportType));
    }
}
