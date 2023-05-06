package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.service.PlanDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/planDay")
public class PlanDayController {
    @Autowired
    private PlanDayService planDayService;

    @PostMapping("/addPlanDay")
    public ResponseEntity<PlanDayDTO> addPlanDays(@RequestBody AddPlanDayDTO addPlanDay) {
        return ResponseEntity.ok(planDayService.addPlanDay(addPlanDay));
    }

    @GetMapping("/getPlanDayEntries/{dayId}")
    public ResponseEntity<List<PlanEntryDTO>> getPlanDayEntries(@PathVariable long dayId) {
        return ResponseEntity.ok(planDayService.getPlanDayEntries(dayId));
    }

    @DeleteMapping("/deletePlanDay/{dayId}")
    public ResponseEntity deletePlanDay(@PathVariable long dayId) {
        planDayService.deletePlanDay(dayId);
        return ResponseEntity.noContent().build();
    }
}
