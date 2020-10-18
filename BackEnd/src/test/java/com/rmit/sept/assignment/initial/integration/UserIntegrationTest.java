package com.rmit.sept.assignment.initial.integration;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.UserService;
import com.rmit.sept.assignment.initial.web.UserController;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class UserIntegrationTest {
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @MockBean
    AuthRequestService authRequestService;

    @MockBean
    UserRepository repo;

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

        when(authRequestService.authUserRequest(any(), (User) any())).thenReturn(true);
        when(authRequestService.authUserRequest(any(), (Long) any())).thenReturn(true);
    }

    @Test
    @DisplayName("Test getUsers")
    void testGetUsers() throws Exception {
        // Mocking service
        when(repo.findAll()).thenReturn(users);

        mockMvc.perform(get("/api/customer/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getUser success")
    void testGetUserSuccess() throws Exception {
        // Mocking service
        when(repo.findById(1L)).thenReturn(Optional.of(users.get(0)));

        mockMvc.perform(get("/api/customer/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test getUser not found")
    void testGetUserFail() throws Exception {
        // Mocking service
        when(repo.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/customer/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewUser success")
    void testCreateNewUserSuccess() throws Exception {
        // Mocking service
        when(repo.save(any())).thenReturn(users.get(0));

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
        when(repo.save(any())).thenReturn(null);

        String inputJson = "null";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/customer")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test updateUser success")
    void testUpdateUserSuccess() throws Exception {
        // Mocking service
        when(repo.save(any())).thenReturn(users.get(0));
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(users.get(0)));

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
    @DisplayName("Test updateUser badRequest")
    void testUpdateUserFail() throws Exception {
        // Mocking service
        when(repo.save(any())).thenReturn(null);

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
