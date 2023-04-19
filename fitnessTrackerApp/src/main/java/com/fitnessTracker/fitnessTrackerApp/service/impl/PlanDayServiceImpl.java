package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.exceptions.PlanDayNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.WorkoutPlanNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanDayRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.PlanDayService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PlanDayServiceImpl implements PlanDayService {
    private final PlanDayRepository planDayRepository;
    private final ModelMapper modelMapper;
    private final WorkoutPlanRepository workoutPlanRepository;

    public PlanDayServiceImpl(PlanDayRepository planDayRepository, WorkoutPlanRepository workoutPlanRepository, ModelMapper modelMapper) {
        this.planDayRepository = planDayRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PlanDayDTO getPlanDayById(long id) {
        PlanDay planDay = planDayRepository.findById(id).orElse(null);
        if(planDay == null) {
            return null;
        }
        return modelMapper.map(planDay, PlanDayDTO.class);
    }

    @Override
    public PlanDayDTO addPlanDay(AddPlanDayDTO planDayDTO) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planDayDTO.getPlanId()).orElse(null);
        if(workoutPlan == null){
            throw new WorkoutPlanNotFoundException("The plan with id " + planDayDTO.getPlanId() + " not found");
        }
        PlanDay planDay = PlanDay.builder()
                .plan(workoutPlan)
                .dayNumber(planDayDTO.getDayNumber())
                .build();

        planDayRepository.save(planDay);
        return modelMapper.map(planDay, PlanDayDTO.class);
    }

    private void decreaseDayNumber(PlanDay planDay) {
        planDay.setDayNumber(planDay.getDayNumber() - 1);
        planDayRepository.save(planDay);
    }

    @Override
    public void deletePlanDay(long id) {
        PlanDay planDay = planDayRepository.findById(id).orElse(null);
        if(planDay == null) {
            throw new PlanDayNotFoundException("Plan day with id " + id + " not found");
        }
        List<PlanDay> workoutPlanDays = planDay.getPlan().getPlanDays();
        for(PlanDay day: workoutPlanDays) {
            if(day.getDayNumber() > planDay.getDayNumber()) {
                decreaseDayNumber(day);
            }
        }
        planDayRepository.deleteById(id);
    }

    @Override
    public PlanDayDTO updatePlanDay(EditPlanDayDTO editPlanDay) {
        PlanDay planDay = planDayRepository.findById(editPlanDay.getId()).orElse(null);
        if(planDay == null) {
            return null;
        }
        planDay.setDayNumber(editPlanDay.getDayNumber());
        planDayRepository.save(planDay);
        return modelMapper.map(planDay, PlanDayDTO.class);
    }

    @Override
    public List<PlanEntryDTO> getPlanDayEntries(long id) {
        PlanDay planDay = planDayRepository.findById(id).orElse(null);
        if(planDay == null) {
            throw new PlanDayNotFoundException("Plan day with id " + id + " not found");
        }
        return planDay.getPlanEntries().stream()
                .map(e -> modelMapper.map(e, PlanEntryDTO.class))
                .sorted(Comparator.comparingInt(PlanEntryDTO::getOrderNumber))
                .toList();
    }
}
