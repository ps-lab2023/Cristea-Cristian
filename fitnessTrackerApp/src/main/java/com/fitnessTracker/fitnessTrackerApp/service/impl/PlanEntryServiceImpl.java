package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.exceptions.PlanDayNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.PlanEntryNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.PlanEntry;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanDayRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanEntryRepository;
import com.fitnessTracker.fitnessTrackerApp.service.PlanEntryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlanEntryServiceImpl implements PlanEntryService {
    private final PlanEntryRepository planEntryRepository;
    private final PlanDayRepository planDayRepository;
    private final ModelMapper modelMapper;

    public PlanEntryServiceImpl(PlanEntryRepository planEntryRepository, PlanDayRepository planDayRepository, ModelMapper modelMapper) {
        this.planEntryRepository = planEntryRepository;
        this.planDayRepository = planDayRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanEntryDTO getPlanEntryById(long id) {
        PlanEntry planEntry = planEntryRepository.findById(id).orElse(null);
        if(planEntry == null) {
            return null;
        }
        return modelMapper.map(planEntry, PlanEntryDTO.class);
    }

    @Override
    public PlanEntryDTO addPlanEntry(AddPlanEntryDTO addPlanEntry) {
        PlanDay planDay = planDayRepository.findById(addPlanEntry.getPlanDayId()).orElse(null);
        if(planDay == null) {
            throw new PlanDayNotFoundException("The plan day with id " + addPlanEntry.getPlanDayId() + " not found");
        }
        PlanEntry planEntry = PlanEntry.builder()
                .planDay(planDay)
                .nameOfExercise(addPlanEntry.getNameOfExercise())
                .noOfReps(addPlanEntry.getNoOfReps())
                .duration(LocalTime.of(addPlanEntry.getDuration().getHours(),
                        addPlanEntry.getDuration().getMinutes(),
                        addPlanEntry.getDuration().getSeconds()))
                .description(addPlanEntry.getDescription())
                .orderNumber(addPlanEntry.getOrderNumber())
                .noOfSets(addPlanEntry.getNoOfSets())
                .restBetween(addPlanEntry.getRestBetween())
                .build();

        planEntryRepository.save(planEntry);
        return modelMapper.map(planEntry, PlanEntryDTO.class);
    }

    private void decreaseOrderNumber(PlanEntry planEntry) {
        planEntry.setOrderNumber(planEntry.getOrderNumber()-1);
        planEntryRepository.save(planEntry);
    }

    @Override
    public void deletePlanEntry(long id) {
        PlanEntry planEntry = planEntryRepository.findById(id).orElse(null);
        if(planEntry == null) {
            throw new PlanEntryNotFoundException("Entry with id " + id + " not found");
        }
        List<PlanEntry> planEntriesFromSameDay = planEntry.getPlanDay().getPlanEntries();
        for(PlanEntry entry: planEntriesFromSameDay) {
            if(entry.getOrderNumber() > planEntry.getOrderNumber()) {
                decreaseOrderNumber(entry);
            }
        }
        planEntryRepository.deleteById(id);
    }

    @Override
    public PlanEntryDTO updatePlanEntry(EditPlanEntryDTO editPlanEntry) {
        PlanEntry planEntry = planEntryRepository.findById(editPlanEntry.getId()).orElse(null);
        if(planEntry == null) {
            throw new PlanEntryNotFoundException("Entry with id " + editPlanEntry.getId() + " not found");
        }
        planEntry.setDescription(editPlanEntry.getDescription());
        planEntry.setNoOfReps(editPlanEntry.getNoOfReps());
        planEntry.setNameOfExercise(editPlanEntry.getNameOfExercise());
        planEntry.setDuration(LocalTime.of(editPlanEntry.getDuration().getHours(),
                editPlanEntry.getDuration().getMinutes(),
                editPlanEntry.getDuration().getSeconds()));
        planEntry.setNoOfSets(editPlanEntry.getNoOfSets());
        planEntry.setRestBetween(editPlanEntry.getRestBetween());
        planEntryRepository.save(planEntry);
        return modelMapper.map(planEntry, PlanEntryDTO.class);
    }
}
