package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserPlanDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.UserPlan;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.UserPlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserPlanServiceTest {
    private UserPlanService service;

    @Mock
    private UserPlanRepository userPlanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private ModelMapper modelMapper;

    private UserPlan userPlan;

    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();

        service = new UserPlanServiceImpl(userPlanRepository, workoutPlanRepository, userRepository, modelMapper);

        User trainer = User.builder()
                .id(1L)
                .email("email1@domain.com")
                .name("name1")
                .gender(GenderEnum.F)
                .username("username1")
                .password("password1")
                .height(178)
                .weight(76)
                .role(UserRoleEnum.TRAINER)
                .build();

        WorkoutPlan workoutPlan = WorkoutPlan.builder()
                .id(1L)
                .trainer(trainer)
                .description("Plan1")
                .mainActivity(ActivityTypeEnum.RUNNING)
                .level(LevelEnum.INTERMEDIATE)
                .duration(LocalTime.of(2, 3, 12))
                .build();

        User user = User.builder()
                .id(2L)
                .email("email2@domain.com")
                .name("name2")
                .gender(GenderEnum.M)
                .username("username2")
                .password("password2")
                .height(178)
                .weight(76)
                .role(UserRoleEnum.USER)
                .build();

        userPlan = UserPlan.builder()
                .id(1L)
                .user(user)
                .plan(workoutPlan)
                .currentDay(3)
                .build();

        when(userPlanRepository.findById(1L)).thenReturn(Optional.ofNullable(userPlan));
        when(userRepository.findById(2L)).thenReturn(Optional.ofNullable(user));
        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.ofNullable(workoutPlan));
    }

    @Test
    void givenValidId_whenGetUserPlanById_thenReturnUserPlan() {
        UserPlanDTO returnedUserPlan = service.getUserPlanById(1L);
        UserPlanDTO expectedUserPlan = modelMapper.map(userPlan, UserPlanDTO.class);

        assertEquals(expectedUserPlan, returnedUserPlan);
    }

    @Test
    void givenInvalidId_whenGetUserPlanById_thenNull() {
        UserPlanDTO returnedUserPlan = service.getUserPlanById(2L);

        assertNull(returnedUserPlan);
    }

    @Test
    void givenValidPlanIdAndUserId_whenAddUserPlan_returnTheAddedUserPlan() {
        AddUserPlanDTO addUserPlan = AddUserPlanDTO.builder()
                .planId(1L)
                .userId(2L)
                .build();

        UserPlanDTO expectedAddedUserPlan = UserPlanDTO.builder()
                .userId(2L)
                .planId(1L)
                .currentDay(1)
                .build();

        UserPlanDTO actualAddedUserPlan = service.addUserPlan(addUserPlan);

        assertEquals(expectedAddedUserPlan, actualAddedUserPlan);
    }

    @Test
    void givenInvalidPlanId_whenAddUserPlan_returnNull() {
        AddUserPlanDTO addUserPlan = AddUserPlanDTO.builder()
                .planId(3L)
                .userId(2L)
                .build();

        UserPlanDTO addedUserPlan = service.addUserPlan(addUserPlan);

        assertNull(addedUserPlan);
    }

    @Test
    void givenInvalidUserId_whenAddUserPlan_returnNull() {
        AddUserPlanDTO addUserPlan = AddUserPlanDTO.builder()
                .planId(1L)
                .userId(5L)
                .build();

        UserPlanDTO addedUserPlan = service.addUserPlan(addUserPlan);

        assertNull(addedUserPlan);
    }

    @Test
    void givenExistingUserPlan_whenUpdateUserPlan_thenReturnTheUpdatedUserPlan() {
        EditUserPlanDTO editUserPlan = EditUserPlanDTO.builder()
                .id(1L)
                .currentDay(6)
                .build();

        UserPlanDTO expectedEditedUserPlan = UserPlanDTO.builder()
                .id(1L)
                .planId(1L)
                .userId(2L)
                .currentDay(6)
                .build();

        UserPlanDTO actualUserPlane = service.updateUserPlan(editUserPlan);

        assertEquals(expectedEditedUserPlan, actualUserPlane);
    }

    @Test
    void givenNonExistingUserPlan_whenUpdateUserPlan_thenReturnNull() {
        EditUserPlanDTO editUserPlan = EditUserPlanDTO.builder()
                .id(2L)
                .currentDay(6)
                .build();

        UserPlanDTO editedUserPlan = service.updateUserPlan(editUserPlan);
        assertNull(editedUserPlan);
    }
}
