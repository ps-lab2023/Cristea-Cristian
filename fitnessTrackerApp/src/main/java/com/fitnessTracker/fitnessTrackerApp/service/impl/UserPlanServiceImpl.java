package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutPlanWithTrainerDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.exceptions.UserNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.UserPlanNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.WorkoutPlanNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.UserPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPlanServiceImpl implements UserPlanService {
    private final UserPlanRepository userPlanRepository;
    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserPlanServiceImpl(UserPlanRepository userPlanRepository, WorkoutPlanRepository workoutPlanRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.userPlanRepository = userPlanRepository;
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserPlanDTO getUserPlanById(long id) {
        UserPlan userPlan = userPlanRepository.findById(id).orElse(null);
        if(userPlan == null) {
            return null;
        }
        return modelMapper.map(userPlan, UserPlanDTO.class);
    }

    @Override
    public UserPlanDTO addUserPlan(AddUserPlanDTO addUserPlan) {
        User user = userRepository.findById(addUserPlan.getUserId()).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.USER) {
            throw new UserNotFoundException("User with id " + addUserPlan.getUserId() + " not found");
        }
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(addUserPlan.getPlanId()).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout with id " + addUserPlan.getPlanId() + " not found");
        }
        UserPlan userPlan = UserPlan.builder()
                .user(user)
                .plan(workoutPlan)
                .currentDay(1)
                .build();

        userPlanRepository.save(userPlan);
        return modelMapper.map(userPlan, UserPlanDTO.class);
    }

    @Override
    public void deleteUserPlan(long userId, long workoutPlanId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(workoutPlanId).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + workoutPlanId + " not found");
        }
        UserPlan userPlan = userPlanRepository.findByUserAndPlan(user, workoutPlan);
        userPlanRepository.deleteById(userPlan.getId());
    }

    @Override
    public UserPlanDTO getUserPlan(long userId, long planId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + planId + " not found");
        }
        return modelMapper.map(userPlanRepository.findByUserAndPlan(user, workoutPlan), UserPlanDTO.class);
    }

    @Override
    public UserPlanDTO updateUserPlan(EditUserPlanDTO editUserPlan) {
        UserPlan userPlan = userPlanRepository.findById(editUserPlan.getId()).orElse(null);
        if(userPlan == null) {
            return null;
        }
        userPlan.setCurrentDay(editUserPlan.getCurrentDay());
        userPlanRepository.save(userPlan);
        return modelMapper.map(userPlan, UserPlanDTO.class);
    }

    @Override
    public List<WorkoutPlanWithTrainerDTO> getUserWorkoutPlans(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.USER) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        return user.getUserPlans().stream()
                .map(up -> up.getPlan())
                .map(w -> {
                    WorkoutPlanWithTrainerDTO mappedPlan= modelMapper.map(w, WorkoutPlanWithTrainerDTO.class);
                    mappedPlan.setTrainerName(w.getTrainer().getName());
                    mappedPlan.setNoOfDays(w.getPlanDays().size());
                    return mappedPlan;
                })
                .toList();
    }

    @Override
    public void updateCurrentDay(long userId, long planId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(planId).orElse(null);
        if(workoutPlan == null) {
            throw new WorkoutPlanNotFoundException("Workout plan with id " + planId + " not found");
        }
        UserPlan userPlan = userPlanRepository.findByUserAndPlan(user, workoutPlan);
        userPlan.setCurrentDay(userPlan.getCurrentDay() + 1);
        userPlanRepository.save(userPlan);
    }
}
