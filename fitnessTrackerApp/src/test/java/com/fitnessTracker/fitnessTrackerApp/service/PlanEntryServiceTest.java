package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.LevelEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.PlanDay;
import com.fitnessTracker.fitnessTrackerApp.model.PlanEntry;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutPlan;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanDayRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.PlanEntryRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.PlanEntryServiceImpl;
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

public class PlanEntryServiceTest {
    private PlanEntryService service;

    @Mock
    private PlanEntryRepository planEntryRepository;

    @Mock
    private PlanDayRepository planDayRepository;

    @Mock
    private ModelMapper modelMapper;

    private PlanEntry planEntry;

    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();

        service = new PlanEntryServiceImpl(planEntryRepository, planDayRepository, modelMapper);

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

        PlanDay planDay = PlanDay.builder()
                .plan(workoutPlan)
                .id(1L)
                .dayNumber(1)
                .isRestDay(false)
                .build();

        planEntry = PlanEntry.builder()
                .id(1L)
                .nameOfExercise("name")
                .description("desc")
                .duration(LocalTime.of(0, 2, 4))
                .noOfReps(5)
                .orderNumber(3)
                .build();

        List<PlanEntry> planEntries = new ArrayList<>();
        planEntries.add(planEntry);

        planDay.setPlanEntries(planEntries);

        when(planEntryRepository.findById(1L)).thenReturn(Optional.ofNullable(planEntry));
        when(planDayRepository.findById(1L)).thenReturn(Optional.of(planDay));
    }

    @Test
    void givenValidId_whenGetPlanEntryById_thenReturnPlanEntry() {
        PlanEntryDTO returnedPlanEntry = service.getPlanEntryById(1L);
        PlanEntryDTO expectedPlanEntry = modelMapper.map(planEntry, PlanEntryDTO.class);

        assertEquals(expectedPlanEntry, returnedPlanEntry);
    }

    @Test
    void givenInvalidId_whenGetPlanEntryById_thenReturnNull() {
        PlanEntryDTO returnedPlanEntry = service.getPlanEntryById(2L);

        assertNull(returnedPlanEntry);
    }

    @Test
    void givenValidPlanDayId_whenAddPlanEntry_returnTheAddedPlanEntry() {
        AddPlanEntryDTO addPlanEntry = AddPlanEntryDTO.builder()
                .planDayId(1L)
                .orderNumber(1)
                .noOfReps(12)
                .nameOfExercise("name2")
                .description("desc2")
                .duration(new DurationDTO(0, 2, 3))
                .build();

        PlanEntryDTO expectedAddedPlanEntry = PlanEntryDTO.builder()
                .planDayId(1L)
                .orderNumber(1)
                .noOfReps(12)
                .nameOfExercise("name2")
                .description("desc2")
                .duration(LocalTime.of(0,2,3))
                .build();

        PlanEntryDTO actualAddedPlanEntry = service.addPlanEntry(addPlanEntry);

        assertEquals(expectedAddedPlanEntry, actualAddedPlanEntry);
    }

    @Test
    void givenInvalidPlanDayId_whenAddPlanEntry_returnNull() {
        AddPlanEntryDTO addPlanEntry = AddPlanEntryDTO.builder()
                .planDayId(2L)
                .orderNumber(1)
                .noOfReps(12)
                .nameOfExercise("name2")
                .description("desc2")
                .duration(new DurationDTO(0, 2, 3))
                .build();

        PlanEntryDTO addedPlanEntry = service.addPlanEntry(addPlanEntry);

        assertNull(addedPlanEntry);
    }

    @Test
    void givenExistingPlanEntry_whenUpdatePlanEntry_thenReturnTheUpdatedPlanEntry() {
        EditPlanEntryDTO editPlanEntry = EditPlanEntryDTO.builder()
                .id(1L)
                .duration(new DurationDTO(0,4, 3))
                .orderNumber(3)
                .nameOfExercise("newName")
                .description("newDesc")
                .noOfReps(15)
                .build();

        PlanEntryDTO expectedEditedPlanEntry = PlanEntryDTO.builder()
                .id(1L)
                .orderNumber(3)
                .noOfReps(15)
                .nameOfExercise("newName")
                .description("newDesc")
                .duration(LocalTime.of(0,4,3))
                .build();

        PlanEntryDTO actualEditedPlanEntry = service.updatePlanEntry(editPlanEntry);
        assertEquals(expectedEditedPlanEntry, actualEditedPlanEntry);
    }

    @Test
    void givenNonExistingPlanEntry_whenUpdatePlanEntry_thenReturnNull() {
        EditPlanEntryDTO editPlanEntry = EditPlanEntryDTO.builder()
                .id(2L)
                .duration(new DurationDTO(0,4, 3))
                .orderNumber(3)
                .nameOfExercise("newName")
                .description("newDesc")
                .noOfReps(15)
                .build();

        PlanEntryDTO editedPlanEntry = service.updatePlanEntry(editPlanEntry);
        assertNull(editedPlanEntry);
    }
}
