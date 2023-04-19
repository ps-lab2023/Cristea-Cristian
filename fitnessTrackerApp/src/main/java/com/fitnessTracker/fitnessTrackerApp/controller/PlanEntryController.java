package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditPlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanEntryDTO;
import com.fitnessTracker.fitnessTrackerApp.service.PlanEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/planEntry")
public class PlanEntryController {
    @Autowired
    private PlanEntryService planEntryService;

    @PostMapping("/addPlanEntry")
    private ResponseEntity<PlanEntryDTO> addPlanEntry(@RequestBody AddPlanEntryDTO addPlanEntry) {
        return ResponseEntity.ok(planEntryService.addPlanEntry(addPlanEntry));
    }

    @DeleteMapping("/deletePlanEntry/{planEntryId}")
    private ResponseEntity deletePlanEntry(@PathVariable long planEntryId) {
        planEntryService.deletePlanEntry(planEntryId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/updatePlanEntry")
    private ResponseEntity<PlanEntryDTO> updatePlanEntry(@RequestBody EditPlanEntryDTO editPlanEntry) {
        return ResponseEntity.ok(planEntryService.updatePlanEntry(editPlanEntry));
    }
}
