package com.fitnessTracker.fitnessTrackerApp.repository;

import com.fitnessTracker.fitnessTrackerApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsernameAndPassword(String username, String password);
    User findByEmail(String email);
}
