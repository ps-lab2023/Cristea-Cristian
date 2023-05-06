package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.ActivityTypeEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.ReportType;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.exceptions.UserNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.WorkoutRecordNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.model.WorkoutRecord;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.repository.WorkoutRecordRepository;
import com.fitnessTracker.fitnessTrackerApp.service.WorkoutRecordService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

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
            throw new WorkoutRecordNotFoundException("Workout record with id " + id + " not found");
        }
        return modelMapper.map(workoutRecord, WorkoutRecordDTO.class);
    }

    @Override
    @Transactional
    public List<WorkoutRecordDTO> getWorkoutRecordsByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null || user.getRole() != UserRoleEnum.USER) {
            throw new UserNotFoundException("User with id " + userId +" not found");
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
            throw new UserNotFoundException("User with id " + addWorkoutRecord.getUserId() +" not found");
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
            throw new WorkoutRecordNotFoundException("Workout record with id " + editWorkoutRecord.getId() + " not found");
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

    @Override
    @Transactional
    public UserWorkoutRecordsDTO getWorkoutRecordsByUser(long userId) {
        Map<YearMonth, List<WorkoutRecordDTO>> monthWorkoutRecords = this.getWorkoutRecordsByUserId(userId).stream()
                .sorted(Comparator.comparing(WorkoutRecordDTO::getDate).reversed())
                .collect(Collectors.groupingBy(record -> YearMonth.of(record.getDate().getYear(),
                        record.getDate().getMonth()), LinkedHashMap::new, Collectors.toList()));

        List<MonthSummaryDTO> monthSummaries = monthWorkoutRecords.entrySet().stream()
                .map(e ->
                {
                    List<WorkoutRecordDTO> workoutRecordsList = e.getValue();
                    return MonthSummaryDTO.builder()
                            .month(e.getKey().getMonth().getValue())
                            .year(e.getKey().getYear())
                            .totalDistance(workoutRecordsList.stream().mapToDouble(WorkoutRecordDTO::getDistance).sum())
                            .totalTime(workoutRecordsList.stream().mapToDouble(w -> w.getDuration().toSecondOfDay()/60.0).sum())
                            .numberOfTimes(workoutRecordsList.size())
                            .calories(workoutRecordsList.stream().mapToInt(WorkoutRecordDTO::getCalories).sum())
                            .workoutRecords(workoutRecordsList)
                            .build();
                }).toList();

        return UserWorkoutRecordsDTO.builder()
                .duration(monthSummaries.stream().mapToDouble(MonthSummaryDTO::getTotalTime).sum()/60.0)
                .numberOfSessions(monthSummaries.stream().mapToInt(MonthSummaryDTO::getNumberOfTimes).sum())
                .calories(monthSummaries.stream().mapToInt(MonthSummaryDTO::getCalories).sum())
                .monthSummaries(monthSummaries)
                .build();
    }

    @Override
    @Transactional
    public UserWorkoutRecordsDTO filterWorkoutRecordsByActivityType(long userId, ActivityTypeEnum activityType) {
        Map<YearMonth, List<WorkoutRecordDTO>> monthWorkoutRecords = this.getWorkoutRecordsByUserId(userId).stream()
                .filter(w -> w.getActivityType().equals(activityType))
                .sorted(Comparator.comparing(WorkoutRecordDTO::getDate).reversed())
                .collect(Collectors.groupingBy(record -> YearMonth.of(record.getDate().getYear(),
                        record.getDate().getMonth()), LinkedHashMap::new, Collectors.toList()));

        List<MonthSummaryDTO> monthSummaries = monthWorkoutRecords.entrySet().stream()
                .map(e ->
                {
                    List<WorkoutRecordDTO> workoutRecordsList = e.getValue();
                    return MonthSummaryDTO.builder()
                            .month(e.getKey().getMonth().getValue())
                            .year(e.getKey().getYear())
                            .totalDistance(workoutRecordsList.stream().mapToDouble(WorkoutRecordDTO::getDistance).sum())
                            .totalTime(workoutRecordsList.stream().mapToDouble(w -> w.getDuration().toSecondOfDay()/60.0).sum())
                            .numberOfTimes(workoutRecordsList.size())
                            .calories(workoutRecordsList.stream().mapToInt(WorkoutRecordDTO::getCalories).sum())
                            .workoutRecords(workoutRecordsList)
                            .build();
                }).toList();

        return UserWorkoutRecordsDTO.builder()
                .duration(monthSummaries.stream().mapToDouble(MonthSummaryDTO::getTotalTime).sum()/60.0)
                .numberOfSessions(monthSummaries.stream().mapToInt(MonthSummaryDTO::getNumberOfTimes).sum())
                .calories(monthSummaries.stream().mapToInt(MonthSummaryDTO::getCalories).sum())
                .monthSummaries(monthSummaries)
                .build();
    }

    private int getNumberOfDays(int month, int year) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    @Override
    @Transactional
    public List<ChartDataDTO> getChartData(long userId, int month, int year, ReportType reportType) {
        Map<Integer, Double> groupedAndSummed = new HashMap<>();
        for(int i = 1; i <= getNumberOfDays(month, year); i++) {
            groupedAndSummed.put(i, 0.0);
        }
        switch (reportType) {
            case DISTANCE -> getWorkoutRecordsByUserId(userId).stream()
                    .filter(w -> w.getDate().getMonth().getValue() == month && w.getDate().getYear() == year)
                    .forEach(w -> groupedAndSummed.merge(w.getDate().getDayOfMonth(), w.getDistance(), Double::sum));
            case CALORIES -> getWorkoutRecordsByUserId(userId).stream()
                    .filter(w -> w.getDate().getMonth().getValue() == month && w.getDate().getYear() == year)
                    .forEach(w -> groupedAndSummed.merge(w.getDate().getDayOfMonth(), (double) w.getCalories(), Double::sum));
            case DURATION -> getWorkoutRecordsByUserId(userId).stream()
                    .filter(w -> w.getDate().getMonth().getValue() == month && w.getDate().getYear() == year)
                    .forEach(w -> groupedAndSummed.merge(w.getDate().getDayOfMonth(), w.getDuration().toSecondOfDay() / 60.0, Double::sum));
        }

        return groupedAndSummed.entrySet().stream()
                .map(e -> ChartDataDTO.builder()
                        .label(Integer.toString(e.getKey()))
                        .value(e.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
