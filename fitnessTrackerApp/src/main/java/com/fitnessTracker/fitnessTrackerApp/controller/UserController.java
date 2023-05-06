package com.fitnessTracker.fitnessTrackerApp.controller;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000/"})
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody AddUserDTO addUser) {
        return ResponseEntity.ok(userService.addUser(addUser));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LogInUserDTO logInUser) {
        return ResponseEntity.ok(userService.logIn(logInUser.getUsername(), logInUser.getPassword()));
    }

    @PostMapping("/addTrainer")
    public ResponseEntity<UserDTO> addTrainer(@RequestBody AddTrainerDTO addTrainer) {
        return ResponseEntity.ok(userService.addTrainer((addTrainer)));
    }

    @PutMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody ChangePasswordDTO changePassword) {
        userService.changePassword(changePassword);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendEmail/{email}")
    public void sendEmail(@PathVariable String email) {
        userService.sendEmail(email);
    }

    @GetMapping("/verifyCode")
    public ResponseEntity<UserDTO> verifyCode(@RequestParam("email") String email, @RequestParam("code") String code) {
        return ResponseEntity.ok(userService.verifyCode(email, code));
    }

    @PostMapping("/logOut/{userId}")
    public ResponseEntity logOut(@PathVariable long userId) {
        userService.logOut(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getNumberOfLoggedInUsers")
    public ResponseEntity<Integer> getNumberOfLoggedInUsers() {
        return ResponseEntity.ok(userService.getNumberOfLoggedInUsers());
    }

    @GetMapping("/getLoggedInUsers")
    public ResponseEntity<List<UserDTO>> getLoggedInUsers() {
        return ResponseEntity.ok(userService.getLoggedInUsers());
    }
}
