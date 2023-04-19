package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private ResponseEntity<UserDTO> registerUser(@RequestBody AddUserDTO addUser) {
        return ResponseEntity.ok(userService.addUser(addUser));
    }

    @PostMapping("/login")
    private ResponseEntity<UserDTO> login(@RequestBody LogInUserDTO logInUser) {
        return ResponseEntity.ok(userService.logIn(logInUser.getUsername(), logInUser.getPassword()));
    }

    @PostMapping("/addTrainer")
    private ResponseEntity<UserDTO> addTrainer(@RequestBody AddTrainerDTO addTrainer) {
        return ResponseEntity.ok(userService.addTrainer((addTrainer)));
    }

    @PutMapping("/changePassword")
    private ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePassword) {
        userService.changePassword(changePassword);
        return ResponseEntity.ok().build();
    }
}
