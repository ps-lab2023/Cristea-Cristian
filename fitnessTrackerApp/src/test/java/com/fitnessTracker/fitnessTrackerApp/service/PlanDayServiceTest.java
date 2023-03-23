package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditPlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.PlanDayDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanDayRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutPlanRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.PlanDayServiceImpl;
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

public class PlanDayServiceTest {
    private PlanDayService service;

    @Mock
    private PlanDayRepository planDayRepository;

    @Mock
    private WorkoutPlanRepository workoutPlanRepository;

    @Mock
    private ModelMapper modelMapper;

    private PlanDay planDay;

    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();
        service = new PlanDayServiceImpl(planDayRepository, workoutPlanRepository, modelMapper);

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
                .duration(LocalTime.of(2,3, 12))
                .build();

        planDay = PlanDay.builder()
                .id(1L)
                .dayNumber(1)
                .isRestDay(false)
                .build();

        List<PlanDay> planDays = new ArrayList<>();
        planDays.add(planDay);

        workoutPlan.setPlanDays(planDays);

        when(planDayRepository.findById(1L)).thenReturn(Optional.ofNullable(planDay));
        when(workoutPlanRepository.findById(1L)).thenReturn(Optional.of(workoutPlan));
    }

    @Test
    void givenValidId_whenGetPlanDayById_thenReturnPlanDay() {
        PlanDayDTO returnedPlanDay = service.getPlanDayById(1L);
        PlanDayDTO expectedWorkoutRecord = modelMapper.map(planDay, PlanDayDTO.class);
        assertEquals(expectedWorkoutRecord, returnedPlanDay);
    }

    @Test
    void givenInvalidId_whenGetPlanDayById_thenReturnNull() {
        PlanDayDTO returnedPlanDay = service.getPlanDayById(2L);

        assertNull(returnedPlanDay);
    }

    @Test
    void givenValidWorkoutPlanId_whenAddPlanDay_returnTheAddedPlanDay() {
        AddPlanDayDTO addPlanDay = AddPlanDayDTO.builder()
                .planId(1L)
                .dayNumber(2)
                .isRestDay(true)
                .build();

        PlanDayDTO expectedAddedPlanDay = PlanDayDTO.builder()
                .planId(1L)
                .dayNumber(2)
                .isRestDay(true)
                .build();

        PlanDayDTO actualAddedPlanDay = service.addPlanDay(addPlanDay);

        assertEquals(expectedAddedPlanDay, actualAddedPlanDay);
    }

    @Test
    void givenInvalidWorkoutPlanId_whenAddPlanDay_returnNull() {
        AddPlanDayDTO addPlanDay = AddPlanDayDTO.builder()
                .planId(2L)
                .dayNumber(2)
                .isRestDay(true)
                .build();

        PlanDayDTO addedPlanDay = service.addPlanDay(addPlanDay);

        assertNull(addedPlanDay);
    }

    @Test
    void givenExistingPlanDay_whenUpdatePlanDay_thenReturnTheUpdatedPlanDay() {
        EditPlanDayDTO editPlanDay = EditPlanDayDTO.builder()
                .id(1L)
                .isRestDay(true)
                .dayNumber(3)
                .build();

        PlanDayDTO expectedEditedPlanDay = PlanDayDTO.builder()
                .id(1L)
                .isRestDay(true)
                .dayNumber(3)
                .planId(0L)
                .build();

        PlanDayDTO actualEditedPlanDay = service.updatePlanDay(editPlanDay);
        assertEquals(expectedEditedPlanDay, actualEditedPlanDay);
    }

    @Test
    void givenNonExistingPlanDay_whenUpdatePlanDay_thenReturnNull() {
        EditPlanDayDTO editPlanDay = EditPlanDayDTO.builder()
                .id(2L)
                .isRestDay(true)
                .dayNumber(3)
                .build();

        PlanDayDTO editedPlanDay = service.updatePlanDay(editPlanDay);
        assertNull(editedPlanDay);
    }

}
