package com.fitnessTracker.fitnessTrackerApp.service;

import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.AddUserDTO;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.EditUserDTO;
import com.fitnessTracker.fitnessTrackerApp.enums.GenderEnum;
import com.fitnessTracker.fitnessTrackerApp.enums.UserRoleEnum;
import com.fitnessTracker.fitnessTrackerApp.model.User;
import com.fitnessTracker.fitnessTrackerApp.dataTransferObject.UserDTO;
import com.fitnessTracker.fitnessTrackerApp.repository.UserRepository;
import com.fitnessTracker.fitnessTrackerApp.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {
    private UserService service;
    @Mock
    private UserRepository userRepository;
    private User user;

    @Mock
    private ModelMapper modelMapper;
    @BeforeEach
    void init() {
        initMocks(this);
        modelMapper = new ModelMapper();
        service = new UserServiceImpl(userRepository, modelMapper);
        user = User.builder()
                .id(1L)
                .email("email1@domain.com")
                .name("name1")
                .gender(GenderEnum.M)
                .username("username1")
                .password("password1")
                .height(178)
                .weight(76)
                .role(UserRoleEnum.USER)
                .build();
        List<User> users = new ArrayList<>();
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(userRepository.findUserByUsernameAndPassword("username1", "password1")).thenReturn(user);
    }

    @Test
    void givenUserWithNewUsername_whenAddUser_thenReturnTheAddedUser(){
        AddUserDTO newUser = AddUserDTO.builder()
                .email("email2@domain.com")
                .name("name2")
                .gender(GenderEnum.F)
                .username("username2")
                .height(165)
                .weight(67)
                .role(UserRoleEnum.USER)
                .build();

        UserDTO expectedAddedUser = UserDTO.builder()
                .username("username2")
                .name("name2")
                .email("email2@domain.com")
                .gender(GenderEnum.F)
                .height(165)
                .weight(67)
                .role(UserRoleEnum.USER)
                .build();

        UserDTO actualAddedUser = service.addUser(newUser);

        assertEquals(expectedAddedUser, actualAddedUser);
    }

    @Test
    void givenUserWithExistingUsername_whenAddUser_thenNull(){
        AddUserDTO newUser = AddUserDTO.builder()
                .email("email2@domain.com")
                .name("name2")
                .gender(GenderEnum.F)
                .username("username1")
                .height(165)
                .weight(67)
                .role(UserRoleEnum.USER)
                .build();

        UserDTO addedUser = service.addUser(newUser);
        assertNull(addedUser);
    }

    @Test
    void givenExistingUser_whenUpdateUser_thenReturnTheUpdatedUser(){
        EditUserDTO editUser = EditUserDTO.builder()
                .id(1L)
                .name("newName")
                .password("newPassword")
                .height(178)
                .weight(76)
                .build();

        UserDTO expectedEditedUser = UserDTO.builder()
                .id(1L)
                .name("newName")
                .username("username1")
                .password("newPassword")
                .email("email1@domain.com")
                .gender(GenderEnum.M)
                .role(UserRoleEnum.USER)
                .weight(76)
                .height(178)
                .build();
        UserDTO actualEditedUser = service.updateUser(editUser);
        assertEquals(expectedEditedUser, actualEditedUser);
    }

    @Test
    void givenNonExistingUser_whenUpdateUser_thenReturnNull(){
        EditUserDTO editUser = EditUserDTO.builder()
                .id(2L)
                .name("newName")
                .password("newPassword")
                .height(178)
                .weight(76)
                .build();

        UserDTO editedUser = service.updateUser(editUser);
        assertNull(editedUser);
    }

    @Test
    void givenValidUsernameAndPassword_whenLogIn_thenReturnUser() {
        UserDTO loggedInUser = service.logIn("username1", "password1");

        assertEquals(modelMapper.map(user, UserDTO.class), loggedInUser);
    }

    @Test
    void givenInvalidUsernameOrPassword_whenLogIn_theReturnNull() {
        UserDTO loggedInUser = service.logIn("username1", "password");

        assertNull(loggedInUser);
    }

    @Test
    void givenExistingUser_whenGetUserRole_thenReturnTheirRole() {
        UserRoleEnum role = service.getUserRole(1L);

        assertEquals(role, UserRoleEnum.USER);
    }

    @Test
    void givenNonExistingUser_whenGetUserRole_thenReturnNull() {
        UserRoleEnum role = service.getUserRole(2L);

        assertNull(role);
    }

    @Test
    void givenThereIsAUser_whenGetAllUsers_thenReturnUser() {
        List<UserDTO> users = service.getAllUsers();

        assert(users).contains(modelMapper.map(user, UserDTO.class));
    }

    @Test
    void givenThereIsNoTrainer_whenGetAllTrainers_thenEmptyList() {
        List<UserDTO> trainers = service.getAllTrainers();

        assert(trainers).isEmpty();
    }
}
