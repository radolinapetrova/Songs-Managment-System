package com.example.radify_be.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.example.radify_be.bussines.UserService;
import com.example.radify_be.bussines.exceptions.InvalidInputException;
import com.example.radify_be.bussines.exceptions.UnsuccessfulAction;
import com.example.radify_be.controller.requests.CreateUserRequest;
import com.example.radify_be.controller.requests.UpdateUserRequest;
import com.example.radify_be.domain.Account;
import com.example.radify_be.domain.Role;
import com.example.radify_be.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#getUser(Integer)}
     */
    @Test
    public void testGetUser4() throws Exception {
        when(userService.getById((Integer) any())).thenReturn(new User());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"account\":null,\"role\":null,\"fname\":null,\"lname\":null}"));
    }

    /**
     * Method under test: {@link UserController#getUser(Integer)}
     */
    @Test
    public void testGetUser5() throws Exception {
        when(userService.getById((Integer) any()))
                .thenReturn(new User(1, "?", "?", new Account("janedoe", "jane.doe@example.org", "iloveyou"), Role.USER));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"account\":{\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"},\"role\""
                                        + ":\"USER\",\"fname\":\"?\",\"lname\":\"?\"}"));
    }

    /**
     * Method under test: {@link UserController#updateUser(UpdateUserRequest)}
     */
    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        user.setAccount(new Account("janedoe", "jane.doe@example.org", "iloveyou"));
        when(userService.updateUser((User) any())).thenReturn(user);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("jane.doe@example.org");
        updateUserRequest.setFirst_name("Jane");
        updateUserRequest.setId(1);
        updateUserRequest.setLast_name("Doe");
        updateUserRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(updateUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("UserResponse(id=null, fName=null, lName=null, username=janedoe, email=jane.doe@example.org)"));
    }

    /**
     * Method under test: {@link UserController#createUser(CreateUserRequest)}
     */
    @Test
    public void testCreateUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("jane.doe@example.org");
        createUserRequest.setFirst_name("Jane");
        createUserRequest.setLast_name("Doe");
        createUserRequest.setPassword("iloveyou");
        createUserRequest.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(createUserRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Integer)}
     */
    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser((Integer) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Successful deletion of user"));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Integer)}
     */
    @Test
    public void testDeleteUser2() throws Exception {
        doNothing().when(userService).deleteUser((Integer) any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders
                .formLogin();
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link UserController#deleteUser(Integer)}
     */
    @Test
    public void testDeleteUser3() throws Exception {
        doThrow(new InvalidInputException()).when(userService).deleteUser((Integer) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }

    /**
     * Method under test: {@link UserController#deleteUser(Integer)}
     */
    @Test
    public void testDeleteUser4() throws Exception {
        doThrow(new UnsuccessfulAction()).when(userService).deleteUser((Integer) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(417))
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("Sadly, it seems that, unfortunately, you have regrettably done absolutely nothing"));
    }

    /**
     * Method under test: {@link UserController#getUser(Integer)}
     */
    @Test
    public void testGetUser() throws Exception {
        when(userService.getById((Integer) any())).thenReturn(new User());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":null,\"account\":null,\"role\":null,\"lname\":null,\"fname\":null}"));
    }

    /**
     * Method under test: {@link UserController#getUser(Integer)}
     */
    @Test
    public void testGetUser2() throws Exception {
        when(userService.getById((Integer) any()))
                .thenReturn(new User(1, "?", "?", new Account("janedoe", "jane.doe@example.org", "iloveyou"), Role.USER));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"account\":{\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\"},\"role\""
                                        + ":\"USER\",\"lname\":\"?\",\"fname\":\"?\"}"));
    }

    /**
     * Method under test: {@link UserController#getUser(Integer)}
     */
    @Test
    public void testGetUser3() throws Exception {
        when(userService.getById((Integer) any())).thenThrow(new InvalidInputException());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(406));
    }
}

