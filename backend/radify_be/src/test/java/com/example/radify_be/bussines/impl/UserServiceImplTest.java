package com.example.radify_be.bussines.impl;

import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.example.radify_be.persistence.UserRepo;
import com.example.radify_be.security.CustomUser;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class})
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepo userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;



    @org.junit.Test
    public void testRegister_shouldRegisterTheUserSuccessfully() throws InvalidInputException {
        User user = new User();
        when(userRepo.save((User) any())).thenReturn(user);
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user1 = new User();
        user1.setAccount(new Account("janedoe", "jane.doe@example.org", "iloveyou"));
        assertSame(user, userServiceImpl.register(user1));
        verify(userRepo).save((User) any());
        verify(passwordEncoder).encode((CharSequence) any());
        assertEquals("secret", user1.getAccount().getPassword());
    }



    @org.junit.Test
    public void testRegister_shouldThrowException_whenUserIsInvalid() throws InvalidInputException {
        when(userRepo.save((User) any())).thenReturn(new User());
        when(passwordEncoder.encode((CharSequence) any())).thenReturn("secret");

        User user = new User();
        user.setAccount(new Account("janedoe", "Email", "iloveyou"));
        assertThrows(InvalidInputException.class, () -> userServiceImpl.register(user));
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

    @org.junit.Test
    public void testGetById_shouldReturnTheUser() {
        User user = new User();
        when(userRepo.findById((Integer) any())).thenReturn(user);
        assertSame(user, userServiceImpl.getById(1));
        verify(userRepo).findById((Integer) any());
    }

    @org.junit.Test
    public void testGetById_shouldThrowException_whenUserIsInvalid() {
        when(userRepo.findById((Integer) any())).thenThrow(new InvalidInputException());
        assertThrows(InvalidInputException.class, () -> userServiceImpl.getById(1));
        verify(userRepo).findById((Integer) any());
    }

    @org.junit.Test
    void validateEmail_shouldThrowExceptionWithInvalidEmail() throws InvalidInputException {
        assertThrows(InvalidInputException.class, () -> userService.validateEmail("radka"));
        assertThrows(InvalidInputException.class, () -> userService.validateEmail("radka.com"));
        assertThrows(InvalidInputException.class, () -> userService.validateEmail("Радолина.com"));
    }


    @org.junit.Test
    public void testDeleteUser_shouldDeleteTheUserSuccessfully() throws Exception {
        when(userRepo.existsById((Integer) any())).thenReturn(false);
        doNothing().when(userRepo).deleteById((Integer) any());
        userServiceImpl.deleteUser(1);
        verify(userRepo).existsById((Integer) any());
        verify(userRepo).deleteById((Integer) any());
    }


    @org.junit.Test
    public void testDeleteUser_shouldThrowException_whenUserIsNotFound() throws Exception {
        when(userRepo.existsById((Integer) any())).thenThrow(new UsernameNotFoundException("Msg"));
        doThrow(new UsernameNotFoundException("Msg")).when(userRepo).deleteById((Integer) any());
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.deleteUser(1));
        verify(userRepo).deleteById((Integer) any());
    }



    @org.junit.Test
    public void testLoadUserByUsername_shouldLoadTheUser() throws UsernameNotFoundException {
        User user = new User(1, "F Name", "L Name", new Account("janedoe", "jane.doe@example.org", "iloveyou"),
                Role.USER);
        user.setRole(Role.USER);
        when(userRepo.findByUsername((String) any())).thenReturn(user);
        UserDetails actualLoadUserByUsernameResult = userServiceImpl.loadUserByUsername("janedoe");
        assertEquals(1, actualLoadUserByUsernameResult.getAuthorities().size());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        assertEquals(1, ((CustomUser) actualLoadUserByUsernameResult).getId().intValue());
        verify(userRepo).findByUsername((String) any());
    }



}