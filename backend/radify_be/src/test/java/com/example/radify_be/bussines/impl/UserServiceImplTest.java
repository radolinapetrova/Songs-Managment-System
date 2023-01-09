package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.exceptions.DublicateDataException;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    //HAPPY FLOW
    @Test
    public void testGetById_shouldReturnTheUser() {
        //ARRANGE
        User user = new User();
        when(userRepo.findById(any(Integer.class))).thenReturn(user);

        //ACT
        User result = userServiceImpl.getById(1);

        //ASSERT
        assertEquals(user, result);
        verify(userRepo).findById(any(Integer.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testGetById_shouldThrowException_whenUserIdIsInvalid() {
        //ARRANGE & ACT
        when(userRepo.findById(any(Integer.class))).thenThrow(new InvalidInputException());

        //ASSERT
        assertThrows(InvalidInputException.class, () -> userServiceImpl.getById(1));
        verify(userRepo).findById(any());
    }


    //HAPPY FLOW
    @Test
    public void testValidateEmail_shouldNotThrowException_whenEmailIsValid() throws InvalidInputException {
        userServiceImpl.validateEmail("radolina.p@gmail.com");
        userServiceImpl.validateEmail("radolina@gmail.com");
    }

    //UNHAPPY FLOW
    @Test
    public void testValidateEmail_shouldThrowException_whenEmailIsInvalid() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> userServiceImpl.validateEmail("radka"));
        assertThrows(InvalidInputException.class, () -> userServiceImpl.validateEmail("radolina.p"));
        assertThrows(InvalidInputException.class, () -> userServiceImpl.validateEmail("radka.p@"));
        assertThrows(InvalidInputException.class, () -> userServiceImpl.validateEmail("radka.gmail.com"));
    }


    //HAPPY FLOW
    @Test
    public void testDeleteUser_shouldNotThrowException_whenTheDeletionWasUnsuccessful() throws InvalidInputException {
        //ARRANGE
        when(userRepo.existsById(any(Integer.class))).thenReturn(true);

        //ACT
        doNothing().when(userRepo).deleteById(any(Integer.class));
        userServiceImpl.deleteUser(1);

        //ASSERT
        verify(userRepo).existsById(any(Integer.class));
        verify(userRepo).deleteById(any(Integer.class));
    }

    //UNHAPPY FLOW
    @Test
    public void testDeleteUser_shouldThrowException_whenTheIdIsInvalid() throws InvalidInputException {
        //ARRANGE AND ACT
        when(userRepo.existsById(any(Integer.class))).thenReturn(false);

        //ASSERT
        assertThrows(InvalidInputException.class, () -> userServiceImpl.deleteUser(1));
        verify(userRepo).existsById(any(Integer.class));
        verify(userRepo, never()).deleteById(any(Integer.class));
    }


    //HAPPY FLOW
    @Test
    public void testUpdateUser_shouldUpdateTheUserSuccessfully() throws InvalidInputException, UnsuccessfulAction {

        //ARRANGE
        User user = User.builder().id(1).fName("Radka").lName("Piratka").role(Role.USER).account(Account.builder().username("radka").email("radka@gmail.com").password("1234").build()).build();
        User user2 = User.builder().id(1).fName("Radolina").lName("Petrova").account(Account.builder().username("radolina").email("radka@gmail.com").build()).build();
        when(userRepo.findById(any(Integer.class))).thenReturn(user);
        when(userRepo.save(user2)).thenReturn(user2);

        //ACT & ASSERT
        User result = userServiceImpl.updateUser(user2);
        assertEquals(user2, result);
        verify(userRepo).save(user2);
    }


    //UNHAPPY FLOW
    @Test
    public void testUpdateUser_shouldThrowException_whenInputIsInvalid() throws InvalidInputException, UnsuccessfulAction {
        //ARRANGE
        User user = User.builder().id(1).fName("Radka").lName("Piratka").role(Role.USER).account(Account.builder().username("radka").email("radka@gmail.com").password("1234").build()).build();
        User user2 = User.builder().id(1).fName("Radolina").lName("Petrova").account(Account.builder().username("radolina").email("radka.com").build()).build();
        when(userRepo.findById(any(Integer.class))).thenReturn(user);

        //ACT & ASSERT
        assertNotEquals(user2, userRepo.findById(1));
        verify(userRepo).findById(1);
        verify(userRepo, never()).save(any(User.class));
    }


    //UNHAPPY FLOW
    @Test
    public void testUpdateUser_shouldThrowException_whenTheDetailsAreNotChanged() throws InvalidInputException, UnsuccessfulAction {
        //ARRANGE
        User user = User.builder().id(1).fName("Radka").lName("Piratka").role(Role.USER).account(Account.builder().username("radka").email("radka@gmail.com").password("1234").build()).build();
        User user2 = User.builder().id(1).fName("Radolina").lName("Petrova").account(Account.builder().username("radolina").email("radka@gmail.com").build()).build();
        when(userRepo.findById(any(Integer.class))).thenReturn(user);
        when(userRepo.save(user2)).thenReturn(user);


        //ACT & ASSERT
        assertThrows(UnsuccessfulAction.class, () -> userServiceImpl.updateUser(user2));
        assertNotEquals(user2, userRepo.findById(any(Integer.class)));
        verify(userRepo, times(2)).findById(any(Integer.class));
    }

    @Test
    public void testRegister_shouldRegisterTheUserSuccessfully() throws InvalidInputException, DublicateDataException {
        //ARRANGE
        User user = User.builder().id(1).fName("Radka").lName("Piratka").role(Role.USER).account(Account.builder().username("radka").email("radka@gmail.com").password("1234").build()).build();
        when(userRepo.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("1234");

        //ACT
        User result = userServiceImpl.register(user);

        //ASSERT
        assertEquals(user, result);
        verify(userRepo).save(user);
        verify(passwordEncoder).encode(any(CharSequence.class));
        assertEquals("1234", user.getAccount().getPassword());
    }


    @Test
    public void testRegister_shouldThrowAnException_whenInputIsInvalid() throws InvalidInputException, DublicateDataException {
        //ARRANGE
        User user = new User();
        user.setAccount(new Account("radka", "radichka", "1234"));

        //ACT & ASSERT
        assertThrows(InvalidInputException.class, () -> userServiceImpl.register(user));
        verify(userRepo, never()).save(any(User.class));
    }


    @Test
    public void testRegister_shouldThrowException_whenTheAccountDataIsAlreadyUsed() throws InvalidInputException {
        //ARRANGE
        User user = new User();
        user.setAccount(new Account("radka", "radka@gmail.com", "1234"));

        //ACT
        when(userRepo.save(any(User.class))).thenThrow(DublicateDataException.class);

        //ASSERT
        assertThrows(DublicateDataException.class, () -> userServiceImpl.register(user));
        verify(userRepo).save(any(User.class));
    }

//    @Test
//    public void testLoadUserByUsername5() throws UsernameNotFoundException {
//        User user = mock(User.class);
//        when(user.getAccount()).thenThrow(new UsernameNotFoundException("Msg"));
//        when(user.getId()).thenThrow(new UsernameNotFoundException("Msg"));
//        when(user.getRole()).thenReturn(Role.USER);
//        doNothing().when(user).setRole((Role) any());
//        user.setRole(Role.USER);
//        when(userRepo.findByUsername((String) any())).thenReturn(user);
//        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("janedoe"));
//        verify(userRepo).findByUsername((String) any());
//        verify(user).getRole();
//        verify(user).getId();
//        verify(user).setRole((Role) any());
//    }


}