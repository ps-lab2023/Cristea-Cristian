package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;

import java.util.List;

public interface UserService {
    UserDTO addUser(AddUserDTO newUser);
    UserDTO addTrainer(AddTrainerDTO newTrainer);
    void deleteUser(long id);
    UserDTO updateUser(EditUserDTO editUser);
    UserDTO logIn(String username, String password);
    UserRoleEnum getUserRole(long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> getAllTrainers();
    void changePassword(ChangePasswordDTO changePassword);
}
