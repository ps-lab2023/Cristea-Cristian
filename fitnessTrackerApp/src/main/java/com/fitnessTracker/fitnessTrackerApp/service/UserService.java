package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;

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
    void sendEmail(String email);
    UserDTO verifyCode(String email, String code);
    void logOut(long id);
    int getNumberOfLoggedInUsers();
    List<UserDTO> getLoggedInUsers();
}
