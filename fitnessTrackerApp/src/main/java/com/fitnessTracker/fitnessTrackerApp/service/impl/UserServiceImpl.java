package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.*;
import com.fitnessTracker.fitnessTrackerApp.exceptions.IncorrectVerificationCodeException;
import com.fitnessTracker.fitnessTrackerApp.model.VerificationCode;
import com.fitnessTracker.fitnessTrackerApp.repository.VerificationCodeRepository;
import com.fitnessTracker.fitnessTrackerApp.service.EmailService;
import com.fitnessTracker.fitnessTrackerApp.util.PasswordHasher;
import com.fitnessTracker.fitnessTrackerApp.util.Utils;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.exceptions.AddUserException;
import com.fitnessTracker.fitnessTrackerApp.exceptions.UserNotFoundException;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private EmailService emailService;

    public UserServiceImpl(UserRepository userRepository, VerificationCodeRepository verificationCodeRepository,ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.verificationCodeRepository = verificationCodeRepository;
    }

    @Override
    public UserDTO addUser(AddUserDTO newUser) {
        List<User> existingUsers = userRepository.findAll();
        for(User user: existingUsers){
            if(user.getUsername().equals(newUser.getUsername())) {
                throw new AddUserException("Invalid username");
            }
        }
        String hashedPassword = PasswordHasher.hashPassword(newUser.getPassword());
        User user = modelMapper.map(newUser, User.class);
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO addTrainer(AddTrainerDTO newTrainer) {
        List<User> existingUsers = userRepository.findAll();
        for(User user: existingUsers){
            if(user.getUsername().equals(newTrainer.getUsername())) {
                throw new AddUserException("Invalid username");
            }
        }
        String password = Utils.generatePassword(10);
        String hashedPassword = PasswordHasher.hashPassword(password);
        User user = modelMapper.map(newTrainer, User.class);
        user.setRole(UserRoleEnum.TRAINER);
        user.setPassword(hashedPassword);
        user.setChangedPassword(false);
        userRepository.save(user);
        UserDTO mappedUser = modelMapper.map(user, UserDTO.class);
        mappedUser.setPassword(password);
        return mappedUser;
    }

    @Override
    public UserDTO updateUser(EditUserDTO editUser) {
        User user = userRepository.findById(editUser.getId()).orElse(null);
        if(user == null) {
            return null;
        }
        user.setName(editUser.getName());
        user.setPassword(editUser.getPassword());
        user.setHeight(editUser.getHeight());
        user.setWeight(editUser.getWeight());
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDTO logIn(String username, String password) {
        String hashedPassword = PasswordHasher.hashPassword(password);
        User user = userRepository.findUserByUsernameAndPassword(username, hashedPassword);
        if(user == null) {
            throw new UserNotFoundException("Invalid credentials");
        }
        user.setLoggedIn(true);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserRoleEnum getUserRole(long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null){
            return null;
        }
        return user.getRole();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == UserRoleEnum.USER)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public List<UserDTO> getAllTrainers() {
        return userRepository.findAll().stream().
                filter(u -> u.getRole() == UserRoleEnum.TRAINER)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();
    }

    @Override
    public void changePassword(ChangePasswordDTO changePassword) {
        User user = userRepository.findById(changePassword.getUserId()).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + changePassword.getUserId() + " not found");
        }
        String hashedPassword = PasswordHasher.hashPassword(changePassword.getPassword());
        user.setPassword(hashedPassword);
        user.setChangedPassword(true);
        user.setLoggedIn(true);
        userRepository.save(user);
    }

    @Override
    public void sendEmail(String email) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("Invalid email");
        }
        String generatedCode = Utils.generatePassword(5);
        VerificationCode verificationCode = user.getVerificationCode();
        if(verificationCode == null) {
            verificationCode = VerificationCode.builder()
                    .code(generatedCode)
                    .user(user)
                    .build();
        }
        else {
            verificationCode.setCode(generatedCode);
        }
        verificationCodeRepository.save(verificationCode);
        emailService.sendEmail(email, generatedCode);
    }

    @Override
    public UserDTO verifyCode(String email, String code) {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("Invalid email");
        }
        VerificationCode verificationCode = verificationCodeRepository.findByUserAndCode(user, code);
        if(verificationCode == null) {
            throw new IncorrectVerificationCodeException("The code is incorrect");
        }
        verificationCodeRepository.delete(verificationCode);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public void logOut(long id) {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new UserNotFoundException("User with id " + id + " not found");
        }
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @Override
    public int getNumberOfLoggedInUsers(){
        return userRepository.findAll().stream()
                .filter(User::isLoggedIn)
                .toList()
                .size();
    }

    @Override
    public List<UserDTO> getLoggedInUsers() {
        return userRepository.findAll().stream()
                .filter(User::isLoggedIn)
                .map(u -> modelMapper.map(u, UserDTO.class))
                .toList();
    }
}
