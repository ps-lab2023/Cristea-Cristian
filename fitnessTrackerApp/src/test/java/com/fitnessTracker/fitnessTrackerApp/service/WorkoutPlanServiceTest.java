package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.UserPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.WorkoutPlanServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WorkoutPlanServiceTest {
    private WorkoutPlanService service;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserPlanRepository userPlanRepository;

    private ModelMapper modelMapper;

    private WorkoutPlan workoutPlan;

    private List<WorkoutPlan> workoutPlans;

    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();
        service = new WorkoutPlanServiceImpl(workoutPlanRepository, userRepository, modelMapper);

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

        workoutPlan = WorkoutPlan.builder()
                .trainer(trainer)
                .description("Plan1")
                .mainActivity(ActivityTypeEnum.RUNNING)
                .level(LevelEnum.INTERMEDIATE)
                .duration(LocalTime.of(2,3, 12))
                .build();

        workoutPlans = new ArrayList<>();
        workoutPlans.add(workoutPlan);

        trainer.setWorkoutPlans(workoutPlans);

        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.ofNullable(workoutPlan));
        when(userRepository.findById(1L)).thenReturn(Optional.of(trainer));
    }

    @Test
    void givenValidId_whenGetWorkoutPlanRecordById_thenReturnWorkoutPlan() {
        WorkoutPlanDTO returnedWorkoutRecord = service.getWorkoutPlanById(1L);
        WorkoutPlanDTO expectedWorkoutRecord = modelMapper.map(workoutPlan, WorkoutPlanDTO.class);
        assertEquals(expectedWorkoutRecord, returnedWorkoutRecord);
    }

    @Test
    void givenInvalidId_whenGetWorkoutPlanRecordById_thenReturnNull() {
        WorkoutPlanDTO returnedWorkoutRecord = service.getWorkoutPlanById(2L);

        assertNull(returnedWorkoutRecord);
    }

    @Test
    void givenValidTrainerId_whenGetWorkoutPlanByTrainerId_thenReturnListOfWorkoutPlans() {
        List<WorkoutPlanDTO> returnedWorkoutPlans = service.getWorkoutPlansByTrainerId(1L);
        List<WorkoutPlanDTO> expectedWorkoutPlans = workoutPlans.stream().map(w -> modelMapper.map(w, WorkoutPlanDTO.class)).toList();

        assertEquals(expectedWorkoutPlans, returnedWorkoutPlans);
    }

    @Test
    void givenInvalidTrainerId_whenGetWorkoutPlanByTrainerId_thenNull() {
        List<WorkoutPlanDTO> returnedWorkoutPlans = service.getWorkoutPlansByTrainerId(2L);

        assertNull(returnedWorkoutPlans);
    }

    @Test
    void givenValidTrainerId_whenAddWorkoutPlan_returnTheAddedWorkoutPlan() {
        AddWorkoutPlanDTO addWorkoutPlan = AddWorkoutPlanDTO.builder()
                .trainerId(1L)
                .description("plan2")
                .mainActivity(ActivityTypeEnum.CYCLING)
                .level(LevelEnum.INTERMEDIATE)
                .duration(new DurationDTO(2, 3, 1))
                .build();

        WorkoutPlanDTO expectedAddedWorkoutPlan = WorkoutPlanDTO.builder()
                .trainerId(1L)
                .description("plan2")
                .mainActivity(ActivityTypeEnum.CYCLING)
                .level(LevelEnum.INTERMEDIATE)
                .duration(LocalTime.of(2, 3, 1))
                .build();

        WorkoutPlanDTO actualAddedWorkoutPlan = service.addWorkoutPlan(addWorkoutPlan);

        assertEquals(expectedAddedWorkoutPlan, actualAddedWorkoutPlan);
    }

    @Test
    void givenInvalidTrainerId_whenAddWorkoutPlan_returnNull() {
        AddWorkoutPlanDTO addWorkoutPlan = AddWorkoutPlanDTO.builder()
                .trainerId(3L)
                .description("plan2")
                .mainActivity(ActivityTypeEnum.CYCLING)
                .level(LevelEnum.INTERMEDIATE)
                .duration(new DurationDTO(2, 3, 1))
                .build();

        WorkoutPlanDTO addedWorkoutPlan = service.addWorkoutPlan(addWorkoutPlan);

        assertNull(addedWorkoutPlan);
    }

    @Test
    void givenExistingWorkoutPlan_whenUpdateWorkoutPlan_thenReturnTheUpdatedWorkoutPlan() {
        EditWorkoutPlanDTO editWorkoutPlan = EditWorkoutPlanDTO.builder()
                .id(1L)
                .mainActivity(ActivityTypeEnum.RUNNING)
                .duration(new DurationDTO(1, 3,4))
                .level(LevelEnum.ADVANCED)
                .description("plan3")
                .build();

        WorkoutPlanDTO actualEditedWorkoutPlan = service.updateWorkoutPlan(editWorkoutPlan);

        WorkoutPlanDTO expectedEditedWorkoutPlan = WorkoutPlanDTO.builder()
                .trainerId(1L)
                .description("plan3")
                .mainActivity(ActivityTypeEnum.RUNNING)
                .level(LevelEnum.ADVANCED)
                .duration(LocalTime.of(1, 3, 4))
                .build();

        assertEquals(expectedEditedWorkoutPlan, actualEditedWorkoutPlan);
    }

    @Test
    void givenNonExistingWorkoutPlan_whenUpdateWorkoutPlan_thenReturnNull() {
        EditWorkoutPlanDTO editWorkoutPlan = EditWorkoutPlanDTO.builder()
                .id(4L)
                .mainActivity(ActivityTypeEnum.RUNNING)
                .duration(new DurationDTO(1, 3,4))
                .level(LevelEnum.ADVANCED)
                .description("plan3")
                .build();

        WorkoutPlanDTO editedWorkoutPlan = service.updateWorkoutPlan(editWorkoutPlan);

        assertNull(editedWorkoutPlan);
    }
}
