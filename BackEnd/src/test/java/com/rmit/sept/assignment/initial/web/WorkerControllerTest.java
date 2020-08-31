package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.WorkerService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class WorkerControllerTest {
    MockMvc mockMvc;

    @Autowired
    WorkerController workerController;

    @MockBean
    WorkerService workerService;
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
    }

    @Test
    @DisplayName("Test getWorkers")
    void testGetWorkers() throws Exception {
        // Mocking service
        when(workerService.findAll()).thenReturn(workers);

        mockMvc.perform(get("/api/worker/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is("2")));
    }

    @Test
    @DisplayName("Test getWorker success")
    void testGetWorkerSuccess() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(workers.get(0));

        mockMvc.perform(get("/api/worker/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    @DisplayName("Test getWorker not found")
    void testGetWorkerFail() throws Exception {
        // Mocking service
        when(workerService.findById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/worker/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewWorker success")
    void testCreateNewWorkerSuccess() throws Exception {
        // Mocking service
        when(workerService.saveOrUpdateWorker(workers.get(0))).thenReturn(workers.get(0));

        String inputJson = workers.get(0).toString();
        mockMvc.perform(post("/api/worker").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andExpect(status().isCreated())
                .andExpect(jsonPath("$[0].id", is(1L)));
    }

    @Test
    @DisplayName("Test createNewWorker badRequest")
    void testCreateNewWorkerFail() throws Exception {
        // Mocking service
        when(workerService.saveOrUpdateWorker(null)).thenReturn(null);

        Worker worker = null;
        String inputJson = worker.toString();
        mockMvc.perform(post("/api/worker").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andExpect(status().isBadRequest());
    }
}
