package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;



    @Test
    void register_shouldReturnNewUserEntity() {

//        User user = User.builder().id(0).role(Role.USER).fName("Radka").lName("Piratka").account(Account.builder().username("radichka").password("1234").email("radak@gmail.com").build()).build();
//        //UserEntity user2 = UserEntity.builder().id(1).role(Role.USER).fName("Stelka").lName("Petelka").username("stelitka").password("4321").build();
//
//        when(userService.).thenReturn(new BCryptPasswordEncoder());
//
//        when(userRepositoryMock.save(user))
//                .thenReturn(user);
//
//        User actualResult;
//
//        try{
//            actualResult = userService.register(user);
//        }
//        catch(Exception e){
//            actualResult = null;
//        }
//
//        assertEquals(actualResult, user);
//
//        verify(userRepositoryMock).save(user);

    }

    @Test
    void getById_shouldReturnTheUserWith() {
         User user = User.builder().id(1).role(Role.USER).fName("Stelka").lName("Petelka").account(Account.builder().username("stelitka").password("4321").build()).build();

        when(userRepositoryMock.findById(1))
                .thenReturn(user);

        User actualResult = userService.getById(1);

        assertEquals(actualResult, user);

        verify(userRepositoryMock).findById(1);
    }


    //Happy flow
    @Test
    void validateEmail_shouldThrowExceptionWithInvalidEmail() throws InvalidInputException {
       assertThrows(InvalidInputException.class, () -> userService.validateEmail("radka"));
       assertThrows(InvalidInputException.class, () -> userService.validateEmail("radka.com"));
       assertThrows(InvalidInputException.class, () -> userService.validateEmail("Радолина.com"));
    }

    @Test
    void deleteUser_shouldReturnNull() {
//
//        User user = User.builder().id(1).role(Role.USER).fName("Stelka").lName("Petelka").account(Account.builder().username("stelitka").password("4321").build()).build();
//
//        when(userRepositoryMock.existsById(1))
//                .thenReturn(false);
//
//        try{
//            userService.deleteUser(user.getId());
//        }
//        catch(Exception e){
//
//        }
//
//
//
//        boolean actualResult = userService.exi(1);
//
//        assertEquals(actualResult, user);
//
//        verify(userRepositoryMock).findById(1);
    }

    @Test
    void loadUserByUsername() {

    }
}