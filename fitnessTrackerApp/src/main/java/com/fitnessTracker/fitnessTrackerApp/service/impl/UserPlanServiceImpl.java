package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.UserPlanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
            return null;
        }
        WorkoutPlan workoutPlan = workoutPlanRepository.findById(addUserPlan.getPlanId()).orElse(null);
        if(workoutPlan == null) {
            return null;
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
    public void deleteUserPlan(long id) {
        userPlanRepository.deleteById(id);
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
}
