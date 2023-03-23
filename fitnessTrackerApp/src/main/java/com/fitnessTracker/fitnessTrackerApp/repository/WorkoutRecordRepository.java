package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.WorkoutRecord;
import org.hibernate.jdbc.Work;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRecordRepository extends JpaRepository<WorkoutRecord, Long> {
}
