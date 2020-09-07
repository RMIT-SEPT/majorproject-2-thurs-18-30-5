package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserControllerTest {
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @MockBean
    UserService userService;
    /**
     * List of sample users
     */
    private List<User> users;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.userController).build();// Standalone context

        User user1 = new User(1L, "ali123", "123Qwe!");
        User user2 = new User(2L, "ali1234", "123Qwe!");
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    @DisplayName("Test getUsers")
    void testGetUsers() throws Exception {
        // Mocking service
        when(userService.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/customer/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getUser success")
    void testGetUserSuccess() throws Exception {
        // Mocking service
        when(userService.findById(1L)).thenReturn(users.get(0));

        mockMvc.perform(get("/api/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test getUser not found")
    void testGetUserFail() throws Exception {
        // Mocking service
        when(userService.findById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/customer/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewUser success")
    void testCreateNewUserSuccess() throws Exception {
        // Mocking service
        when(userService.saveOrUpdateUser(ArgumentMatchers.any(User.class))).thenReturn(users.get(0));

        String inputJson = "{\n" +
                "    \"id\": 1,\n" +
                "    \"username\":\"ali123\",\n" +
                "    \"password\":\"123Qwe!\"\n" +
                "}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test createNewUser badRequest")
    void testCreateNewUserFail() throws Exception {
        // Mocking service
        when(userService.saveOrUpdateUser(null)).thenReturn(null);

        String inputJson = "null";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}
