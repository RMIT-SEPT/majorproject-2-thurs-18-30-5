package com.rmit.sept.assignment.initial.integration;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.security.JwtResponse;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.WorkerService;
import com.rmit.sept.assignment.initial.web.WorkerController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class WorkerIntegrationTest {
    MockMvc mockMvc;

    @Autowired
    WorkerController workerController;

    @Autowired
    WorkerService workerService;

    @MockBean
    AuthRequestService authRequestService;

    @MockBean
    WorkerRepository repo;

    @MockBean
    BCryptPasswordEncoder encoder;

    /**
     * List of sample workers
     */
    private List<Worker> workers;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.workerController).build();// Standalone context

        Worker worker1 = new Worker(new User(1L, "ali123", "123Qwe!"));
        Worker worker2 = new Worker(new User(2L, "ali1234", "123Qwe!"));
        workers = new ArrayList<>();
        workers.add(worker1);
        workers.add(worker2);

        when(authRequestService.authWorkerRequest(any(), (Worker) any())).thenReturn(true);
        when(authRequestService.authWorkerRequest(any(), (Long) any())).thenReturn(true);
        when(encoder.matches(anyString(), anyString())).thenReturn(true);
    }

    @Test
    @DisplayName("Test getWorkers")
    void testGetWorkers() throws Exception {
        // Mocking service
        when(repo.findAll()).thenReturn(workers);

        mockMvc.perform(get("/api/worker/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getWorker success")
    void testGetWorkerSuccess() throws Exception {
        // Mocking service
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(workers.get(0)));

        mockMvc.perform(get("/api/worker/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test getWorker not found")
    void testGetWorkerFail() throws Exception {
        // Mocking service
        when(repo.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/worker/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test AuthenticateWorker success")
    void testAuthenticateWorkerSuccess() throws Exception {
        // Mocking service
//        when(workerService.authenticateWorker(1L, "123Qwe!", false)).thenReturn(workers.get(0));
        Worker temp = workers.get(0);
        when(authRequestService.getJwtResponse(temp.getUser().getUsername(), temp.getUser().getPassword()))
                .thenReturn(new JwtResponse("token", 1L, temp.getUser().getUsername()));
        when(repo.findByIdAndIsAdmin(1L, false)).thenReturn(Optional.of(temp));
        mockMvc.perform(get("/api/worker/auth/id/1").param("password", "123Qwe!")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test AuthenticateWorker fail")
    void testAuthenticateWorkerFail() throws Exception {
        // Mocking service
        Worker temp = workers.get(0);
        when(authRequestService.getJwtResponse(temp.getUser().getUsername(), temp.getUser().getPassword()))
                .thenReturn(new JwtResponse("token", 1L, temp.getUser().getUsername()));
        when(repo.findByIdAndIsAdmin(1L, false)).thenReturn(Optional.of(temp));
        mockMvc.perform(get("/api/worker/auth/id/3").param("password", "randomPass")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkerByBusiness success")
    void testGetWorkerByBusinessSuccess() throws Exception {
        // Mocking service
        when(repo.findAllByBusiness_IdAndIsAdmin(1L, false)).thenReturn(workers);
        when(repo.findAllByBusiness_Id(1L)).thenReturn(workers);

        mockMvc.perform(get("/api/worker/business/1").param("isAdmin", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getWorkerByBusiness fail (empty)")
    void testGetWorkerByBusinessEmpty() throws Exception {
        // Mocking service
        when(repo.findAllByBusiness_IdAndIsAdmin(3L, false)).thenReturn(new ArrayList<>());
        when(repo.findAllByBusiness_Id(3L)).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/worker/business/3").param("isAdmin", "0")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewWorker success")
    void testCreateNewWorkerSuccess() throws Exception {
        // Mocking service
        when(repo.save(workers.get(0))).thenReturn(workers.get(0));

        String inputJson = "{\"id\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/worker")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test createNewWorker badRequest")
    void testCreateNewWorkerFail() throws Exception {
        // Mocking service
        when(repo.save(workers.get(0))).thenReturn(null);

        String inputJson = "{\"id\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/worker")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test updateWorker success")
    void testUpdateWorkerSuccess() throws Exception {
        // Mocking service
        when(repo.findById(1L)).thenReturn(Optional.ofNullable(workers.get(0)));
        when(repo.save(workers.get(0))).thenReturn(workers.get(0));

        String inputJson = "{\"id\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/worker")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test updateWorker badRequest")
    void testUpdateWorkerFail() throws Exception {
        // Mocking service
        when(repo.findById(1L)).thenReturn(Optional.empty());
        when(repo.save(workers.get(0))).thenReturn(workers.get(0));

        String inputJson = "{\"id\":1}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/worker")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
