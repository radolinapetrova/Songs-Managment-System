package com.example.radify_be.bussines.impl;

import com.example.radify_be.domain.Role;
import com.example.radify_be.persistence.DBRepositories.UserDBRepository;
import com.example.radify_be.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDBRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_shouldReturnNewUserEntity() {

//        UserEntity user1 = UserEntity.builder().id(0).role(Role.USER).fName("Radka").lName("Piratka").username("radichka").password("1234").build();
//        UserEntity user2 = UserEntity.builder().id(1).role(Role.USER).fName("Stelka").lName("Petelka").username("stelitka").password("4321").build();

//        when(userRepositoryMock.save(user1))
//                .thenReturn()

    }

    @Test
    void getById() {
         UserEntity user = UserEntity.builder().id(1).role(Role.USER).fName("Stelka").lName("Petelka").username("stelitka").password("4321").build();

        when(userRepositoryMock.findById(1))
                .thenReturn(Optional.of(user));

        //UserEntity actualResult = userService.getById(1);

//        assertEquals(actualResult, user);
//
//        verify(userRepositoryMock).findById(1);
    }

    @Test
    void validateEmail_shouldThrowExceptionWithInvalidEmail() throws Exception {
       assertThrows(Exception.class, () -> userService.validateEmail("radka"));
       assertThrows(Exception.class, () -> userService.validateEmail("radka.com"));
       assertThrows(Exception.class, () -> userService.validateEmail("Радолина.com"));
       assertThrows(Exception.class, () -> userService.validateEmail("radka@com"));
    }
}