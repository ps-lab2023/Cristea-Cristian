package com.fitnessTracker.fitnessTrackerApp.service.impl;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO addUser(AddUserDTO newUser) {
        List<User> existingUsers = userRepository.findAll();
        for(User user: existingUsers){
            if(user.getUsername().equals(newUser.getUsername())) {
                return null;
            }
        }
        User user = modelMapper.map(newUser, User.class);
        userRepository.save(user);
        return modelMapper.map(user, UserDTO.class);
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
        User user = userRepository.findUserByUsernameAndPassword(username, password);
        if(user == null) {
            return null;
        }
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
}