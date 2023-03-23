package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutPlanService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.List;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {
    private final WorkoutPlanRepository workoutPlanRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public WorkoutPlanServiceImpl(WorkoutPlanRepository workoutPlanRepository,
                                  UserRepository userRepository,
                                  ModelMapper modelMapper) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkoutPlanDTO addWorkoutPlan(AddWorkoutPlanDTO newPlan) {
        User owner = userRepository.findById(newPlan.getTrainerId()).orElse(null);
        if(owner == null || owner.getRole() != UserRoleEnum.TRAINER) {
            return null;
        }
        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .trainer(owner)
                .mainActivity(newPlan.getMainActivity())
                .description(newPlan.getDescription())
                .level(newPlan.getLevel())
                .duration(LocalTime.of(newPlan.getDuration().getHours(),
                        newPlan.getDuration().getMinutes(),
                        newPlan.getDuration().getSeconds()))
                .build();
        workoutPlanRepository.save(workoutPlan);

        return modelMapper.map(workoutPlan, WorkoutPlanDTO.class);
    }

    @Override
    public WorkoutPlanDTO getWorkoutPlanById(long id) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id).orElse(null);
        if(workoutPlan == null) {
            return null;
        }
        return modelMapper.map(workoutPlan, WorkoutPlanDTO.class);
    }

    @Override
    @Transactional
    public List<WorkoutPlanDTO> getWorkoutPlansByTrainerId(long trainerId) {
        User user = userRepository.findById(trainerId).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.TRAINER) {
            return null;
        }
        List<WorkoutPlan> workoutPlans = user.getWorkoutPlans();
        Hibernate.initialize(workoutPlans);
        if(workoutPlans == null) {
            return null;
        }
        return workoutPlans.stream().map(w -> modelMapper.map(w, WorkoutPlanDTO.class)).toList();
    }

    @Override
    public void deleteWorkoutPlan(long id) {
        workoutPlanRepository.deleteById(id);
    }

    @Override
    public WorkoutPlanDTO updateWorkoutPlan(EditWorkoutPlanDTO editWorkoutPlanDTO) {
        WorkoutPlan oldWorkoutPlan = workoutPlanRepository.findById(editWorkoutPlanDTO.getId()).orElse(null);
        if(oldWorkoutPlan == null) {
            return null;
        }
        oldWorkoutPlan.setMainActivity(editWorkoutPlanDTO.getMainActivity());
        oldWorkoutPlan.setDescription(editWorkoutPlanDTO.getDescription());
        oldWorkoutPlan.setDuration(LocalTime.of(editWorkoutPlanDTO.getDuration().getHours(),
                editWorkoutPlanDTO.getDuration().getMinutes(),
                editWorkoutPlanDTO.getDuration().getSeconds()));
        oldWorkoutPlan.setLevel(editWorkoutPlanDTO.getLevel());
        workoutPlanRepository.save(oldWorkoutPlan);
        return modelMapper.map(oldWorkoutPlan, WorkoutPlanDTO.class);
    }
}
