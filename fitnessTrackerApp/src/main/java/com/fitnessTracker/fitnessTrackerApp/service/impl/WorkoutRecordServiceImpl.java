package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditWorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.WorkoutRecordDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutRecord;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutRecordRepository;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutRecordService;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class WorkoutRecordServiceImpl implements WorkoutRecordService {
    private final WorkoutRecordRepository workoutRecordRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public WorkoutRecordServiceImpl(WorkoutRecordRepository workoutRecordRepository,
                                    UserRepository userRepository,
                                    ModelMapper modelMapper) {
        this.workoutRecordRepository = workoutRecordRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public WorkoutRecordDTO getWorkoutRecordById(long id) {
        WorkoutRecord workoutRecord= workoutRecordRepository.findById(id).orElse(null);
        if(workoutRecord == null) {
            return null;
        }
        return modelMapper.map(workoutRecord, WorkoutRecordDTO.class);
    }

    @Override
    @Transactional
    public List<WorkoutRecordDTO> getWorkoutRecordsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.USER) {
            return null;
        }
        List<WorkoutRecord> workoutRecords = user.getWorkoutRecords();
        Hibernate.initialize(workoutRecords);
        if(workoutRecords == null) {
            return null;
        }
        return workoutRecords.stream().map(w -> modelMapper.map(w, WorkoutRecordDTO.class)).toList();
    }

    @Override
    public WorkoutRecordDTO addWorkoutRecord(AddWorkoutRecordDTO addWorkoutRecord) {
        User user = userRepository.findById(addWorkoutRecord.getUserId()).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.USER) {
            return null;
        }
        WorkoutRecord workoutRecord = WorkoutRecord.builder()
                .activityType(addWorkoutRecord.getActivityType())
                .distance(addWorkoutRecord.getDistance())
                .user(user)
                .duration(LocalTime.of(addWorkoutRecord.getDuration().getHours(),
                        addWorkoutRecord.getDuration().getMinutes(),
                        addWorkoutRecord.getDuration().getSeconds()))
                .calories(addWorkoutRecord.getCalories())
                .date(LocalDateTime.now())
                .build();
        workoutRecordRepository.save(workoutRecord);
        return modelMapper.map(workoutRecord, WorkoutRecordDTO.class);
    }

    @Override
    public void deleteWorkoutRecord(long id) {
        workoutRecordRepository.deleteById(id);
    }

    @Override
    public WorkoutRecordDTO updateWorkoutRecord(EditWorkoutRecordDTO editWorkoutRecord) {
        WorkoutRecord workoutRecord = workoutRecordRepository.findById(editWorkoutRecord.getId()).orElse(null);
        if(workoutRecord == null) {
            return null;
        }
        workoutRecord.setActivityType(editWorkoutRecord.getActivityType());
        workoutRecord.setDistance(editWorkoutRecord.getDistance());
        workoutRecord.setDuration(LocalTime.of(editWorkoutRecord.getDuration().getHours(),
                editWorkoutRecord.getDuration().getMinutes(),
                editWorkoutRecord.getDuration().getSeconds()));
        workoutRecord.setCalories(editWorkoutRecord.getCalories());
        workoutRecordRepository.save(workoutRecord);
        return modelMapper.map(workoutRecord, WorkoutRecordDTO.class);
    }
}
