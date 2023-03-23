package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;

import java.util.List;

public interface UserService {
    UserDTO addUser(AddUserDTO newUser);
    void deleteUser(long id);
    UserDTO updateUser(EditUserDTO editUser);
    UserDTO logIn(String username, String password);
    UserRoleEnum getUserRole(long id);
    List<UserDTO> getAllUsers();
    List<UserDTO> getAllTrainers();
}
