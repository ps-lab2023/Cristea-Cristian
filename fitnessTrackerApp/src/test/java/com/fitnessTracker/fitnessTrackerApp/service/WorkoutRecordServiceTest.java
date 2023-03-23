package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.DurationDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutRecord;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutRecordRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.WorkoutRecordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class WorkoutRecordServiceTest {
    private WorkoutRecordService service;

    @Mock
    private WorkoutRecordRepository workoutRecordRepository;

    @Mock
    private UserRepository userRepository;

    private WorkoutRecord workoutRecord;

    private ModelMapper modelMapper;

    List<WorkoutRecord> workoutRecords;
    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();
        service = new WorkoutRecordServiceImpl(workoutRecordRepository, userRepository, modelMapper);

        User user = User.builder()
                .id(1L)
                .email("email1@domain.com")
                .name("name1")
                .gender(GenderEnum.M)
                .username("username1")
                .password("password1")
                .height(178)
                .weight(76)
                .role(UserRoleEnum.USER)
                .build();

        workoutRecord = WorkoutRecord.builder()
                .id(1L)
                .activityType(ActivityTypeEnum.RUNNING)
                .date(LocalDateTime.now())
                .calories(123)
                .user(user)
                .distance(2.5)
                .duration(LocalTime.of(2, 3, 12))
                .build();

        workoutRecords = new ArrayList<>();
        workoutRecords.add(workoutRecord);

        user.setWorkoutRecords(workoutRecords);
        when(workoutRecordRepository.findById(1L)).thenReturn(Optional.ofNullable(workoutRecord));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    }

    @Test
    void givenValidId_whenGetWorkoutRecordById_thenReturnWorkoutRecord() {
        WorkoutRecordDTO returnedWorkoutRecord = service.getWorkoutRecordById(1L);
        WorkoutRecordDTO expectedWorkoutRecord = modelMapper.map(workoutRecord, WorkoutRecordDTO.class);
        assertEquals(expectedWorkoutRecord, returnedWorkoutRecord);
    }

    @Test
    void givenInvalidId_whenGetWorkoutRecordById_thenReturnNull() {
        WorkoutRecordDTO returnedWorkoutRecord = service.getWorkoutRecordById(2L);

        assertNull(returnedWorkoutRecord);
    }

    @Test
    void givenValidUserId_whenGetWorkoutRecordsByUserId_thenReturnListOfWorkoutRecords() {
        List<WorkoutRecordDTO> returnedWorkoutRecords = service.getWorkoutRecordsByUserId(1L);
        List<WorkoutRecordDTO> expectedWorkoutRecords = workoutRecords.stream().map(w -> modelMapper.map(w, WorkoutRecordDTO.class)).toList();
        assertEquals(returnedWorkoutRecords, expectedWorkoutRecords);
    }

    @Test
    void givenInvalidUserId_whenGetWorkoutRecordsByUserId_thenReturnNull() {
        List<WorkoutRecordDTO> returnedWorkoutRecords = service.getWorkoutRecordsByUserId(2L);

        assertNull(returnedWorkoutRecords);
    }

    @Test
    void givenValidUserId_whenAddWorkoutRecord_thenReturnTheAddedWorkoutId() {
        AddWorkoutRecordDTO addWorkoutRecord = AddWorkoutRecordDTO.builder()
                .activityType(ActivityTypeEnum.WALKING)
                .distance(12.3)
                .calories(123)
                .userId(1L)
                .duration(new DurationDTO(1, 2, 23))
                .build();

        WorkoutRecordDTO addedWorkoutRecord = service.addWorkoutRecord(addWorkoutRecord);

        assertNotNull(addedWorkoutRecord);
    }

    @Test
    void givenInvalidUserId_whenAddWorkoutRecord_thenReturnNull() {
        AddWorkoutRecordDTO addWorkoutRecord = AddWorkoutRecordDTO.builder()
                .activityType(ActivityTypeEnum.WALKING)
                .distance(12.3)
                .calories(123)
                .userId(2L)
                .duration(new DurationDTO(1, 2, 23))
                .build();

        WorkoutRecordDTO addedWorkoutRecord = service.addWorkoutRecord(addWorkoutRecord);

        assertNull(addedWorkoutRecord);
    }


    @Test
    void givenExistingWorkoutRecord_whenUpdateWorkoutRecord_thenReturnTheUpdatedWorkoutRecord() {
        EditWorkoutRecordDTO editWorkoutRecord = EditWorkoutRecordDTO.builder()
                .id(1L)
                .activityType(ActivityTypeEnum.RUNNING)
                .distance(12.3)
                .calories(125)
                .duration(new DurationDTO(1, 23, 2))
                .build();

        WorkoutRecordDTO expectedEditedWorkoutRecord = WorkoutRecordDTO.builder()
                .id(1L)
                .userId(1L)
                .date(workoutRecord.getDate())
                .activityType(ActivityTypeEnum.RUNNING)
                .distance(12.3)
                .calories(125)
                .duration(LocalTime.of(1,23,2))
                .build();

        WorkoutRecordDTO actualEditedWorkoutRecord = service.updateWorkoutRecord(editWorkoutRecord);

        assertEquals(expectedEditedWorkoutRecord, actualEditedWorkoutRecord);
    }

    @Test
    void givenNonExistingWorkoutRecord_whenUpdateWorkoutRecord_thenReturnNull() {
        EditWorkoutRecordDTO editWorkoutRecord = EditWorkoutRecordDTO.builder()
                .id(2L)
                .activityType(ActivityTypeEnum.RUNNING)
                .distance(12.3)
                .calories(125)
                .duration(new DurationDTO(1, 23, 2))
                .build();

        WorkoutRecordDTO editedWorkoutRecord = service.updateWorkoutRecord(editWorkoutRecord);

        assertNull(editedWorkoutRecord);
    }
}
