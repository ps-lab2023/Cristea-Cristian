package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.exceptions.UserNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.WorkoutPlanNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutPlanService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
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
            throw new UserNotFoundException("Trainer with id " + newPlan.getTrainerId() + " not found");
        }
        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .trainer(owner)
                .name(newPlan.getName())
                .mainActivity(newPlan.getMainActivity())
                .description(newPlan.getDescription())
                .level(newPlan.getLevel())
                .goal(newPlan.getGoal())
                .build();
        workoutPlanRepository.save(workoutPlan);

        return modelMapper.map(workoutPlan, WorkoutPlanDTO.class);
    }

    @Override
    public WorkoutPlanSummaryDTO getWorkoutPlanById(long id) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + id + " not found");
        }
        WorkoutPlanSummaryDTO mappedPlan = modelMapper.map(workoutPlan, WorkoutPlanSummaryDTO.class);
        mappedPlan.setNoOfDays(workoutPlan.getPlanDays().size());
        return mappedPlan;
    }

    @Override
    @Transactional
    public List<WorkoutPlanSummaryDTO> getWorkoutPlansByTrainerId(long trainerId) {
        User user = userRepository.findById(trainerId).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.TRAINER) {
            throw new UserNotFoundException("Trainer with id " + trainerId + " not found");
        }
        List<WorkoutPlan> workoutPlans = user.getWorkoutPlans();
        Hibernate.initialize(workoutPlans);
        if(workoutPlans == null) {
            return null;
        }
        return workoutPlans.stream().map(w ->
        {
            WorkoutPlanSummaryDTO mappedPlan = modelMapper.map(w, WorkoutPlanSummaryDTO.class);
            mappedPlan.setNoOfDays(w.getPlanDays().size());
            return mappedPlan;
        }).toList();
    }

    @Override
    public void deleteWorkoutPlan(long id) {
        workoutPlanRepository.deleteById(id);
    }

    @Override
    public WorkoutPlanDTO updateWorkoutPlan(EditWorkoutPlanDTO editWorkoutPlanDTO) {
        WorkoutPlan oldWorkoutPlan = workoutPlanRepository.findById(editWorkoutPlanDTO.getId()).orElse(null);
        if(oldWorkoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + editWorkoutPlanDTO.getId() + " not found");
        }
        oldWorkoutPlan.setName(editWorkoutPlanDTO.getName());
        oldWorkoutPlan.setMainActivity(editWorkoutPlanDTO.getMainActivity());
        oldWorkoutPlan.setDescription(editWorkoutPlanDTO.getDescription());
        oldWorkoutPlan.setLevel(editWorkoutPlanDTO.getLevel());
        oldWorkoutPlan.setGoal(editWorkoutPlanDTO.getGoal());
        workoutPlanRepository.save(oldWorkoutPlan);
        return modelMapper.map(oldWorkoutPlan, WorkoutPlanDTO.class);
    }

    @Override
    public List<PlanDaySummaryDTO> getWorkoutPlanDays(long id) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + id + " not found");
        }
        List<PlanDaySummaryDTO> planDaySummaryList = new ArrayList<>();
        for(PlanDay planDay: workoutPlan.getPlanDays()) {
            PlanDaySummaryDTO planDaySummary = PlanDaySummaryDTO.builder()
                    .id(planDay.getId())
                    .dayNumber(planDay.getDayNumber())
                    .noOfEntries(planDay.getPlanEntries().size())
                    .build();
            planDaySummaryList.add(planDaySummary);
        }
        return planDaySummaryList.stream()
                .sorted(Comparator.comparingInt(PlanDaySummaryDTO::getDayNumber))
                .toList();
    }

    @Override
    public List<WorkoutPlanWithTrainerDTO> getNewWorkoutPlans(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        List<Long> usePlansIds = user.getUserPlans().stream()
                .map(up -> up.getPlan().getId())
                .toList();

        return workoutPlanRepository.findAll().stream()
                .filter(w -> !usePlansIds.contains(w.getId()))
                .map(w -> {
                    WorkoutPlanWithTrainerDTO mappedPlan= modelMapper.map(w, WorkoutPlanWithTrainerDTO.class);
                    mappedPlan.setTrainerName(w.getTrainer().getName());
                    mappedPlan.setNoOfDays(w.getPlanDays().size());
                    return mappedPlan;
                })
                .toList();
    }

    @Override
    public WorkoutPlanDTO getWorkoutPlan(long id) {
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(id).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + id + " not found");
        }
        return modelMapper.map(workoutPlan, WorkoutPlanDTO.class);
    }
}
