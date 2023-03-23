package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanDayRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.PlanDayService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
            return null;
        }
        PlanDay planDay = PlanDay.builder()
                .plan(workoutPlan)
                .dayNumber(planDayDTO.getDayNumber())
                .isRestDay(planDayDTO.isRestDay())
                .build();

        planDayRepository.save(planDay);
        return modelMapper.map(planDay, PlanDayDTO.class);
    }

    @Override
    public void deletePlanDay(long id) {
        planDayRepository.deleteById(id);
    }

    @Override
    public PlanDayDTO updatePlanDay(EditPlanDayDTO editPlanDay) {
        PlanDay planDay = planDayRepository.findById(editPlanDay.getId()).orElse(null);
        if(planDay == null) {
            return null;
        }
        planDay.setDayNumber(editPlanDay.getDayNumber());
        planDay.setRestDay(editPlanDay.isRestDay());
        planDayRepository.save(planDay);
        return modelMapper.map(planDay, PlanDayDTO.class);
    }
}
