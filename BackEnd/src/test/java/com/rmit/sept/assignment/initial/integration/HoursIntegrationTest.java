package com.rmit.sept.assignment.initial.integration;

import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.repositories.HoursRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.HoursService;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.WorkerService;
import com.rmit.sept.assignment.initial.web.HoursController;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
public class HoursIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    HoursController hoursController;

    @Autowired
    HoursService hoursService;

    @Autowired
    WorkerService workerService;

    @MockBean
    AuthRequestService authRequestService;

    @MockBean
    WorkerRepository workerRepo;
    @MockBean
    HoursRepository repo;

    private Worker worker;
    private List<Hours> hours;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.hoursController).build();// Standalone context

        worker = new Worker(new User(1L, "ali123", "123Qwe!"));
        hours = new ArrayList<Hours>();

        Hours.HoursPK hoursPK = new Hours.HoursPK(worker, DayOfWeek.FRIDAY);
        Hours h1 = new Hours();
        h1.setId(hoursPK);
        hours.add(h1);

        Hours.HoursPK hoursPK2 = new Hours.HoursPK(worker, DayOfWeek.SATURDAY);
        Hours h2 = new Hours();
        h2.setId(hoursPK2);
        hours.add(h2);

        when(authRequestService.authWorkerRequest(any(), (Worker) any())).thenReturn(true);
        when(authRequestService.authWorkerRequest(any(), (Long) any())).thenReturn(true);
    }

    @Test
    @DisplayName("Test getWorkersHours success")
    void testGetWorkerHoursSuccess() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.findById_Worker(worker)).thenReturn(hours);
        when(repo.findById_WorkerId(worker.getId())).thenReturn(hours);

        mockMvc.perform(get("/api/hours/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.dayOfWeek", is(DayOfWeek.FRIDAY.toString())))
                .andExpect(jsonPath("$[1].id.dayOfWeek", is(DayOfWeek.SATURDAY.toString())));
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null worker)")
    void testGetWorkerHoursFail() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/hours/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkersHours success")
    void testGetWorkerHoursByDaySuccess() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.findById(new Hours.HoursPK(worker, DayOfWeek.FRIDAY))).thenReturn(Optional.of(hours.get(0)));

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "FRIDAY").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.dayOfWeek", is(DayOfWeek.FRIDAY.toString())));
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null worker)")
    void testGetWorkerHoursByDayFailNullWorker() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "FRIDAY").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null hours)")
    void testGetWorkerHoursByDayFailNullHours() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.findById(new Hours.HoursPK(worker, DayOfWeek.FRIDAY))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "FRIDAY").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkerAvailableHours success")
    void testGetWorkerAvailableHoursBySuccess() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.findById(new Hours.HoursPK(worker, LocalDate.of(2020, 10, 15).getDayOfWeek()))).thenReturn(Optional.of(hours.get(0)));
//        when(hoursService.findAvailableByWorker(worker, LocalDate.of(2020, 10, 15))).thenReturn(hours);

        mockMvc.perform(get("/api/hours/available/1").param("date", "2020-10-15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.dayOfWeek", is(DayOfWeek.FRIDAY.toString())));
    }

    @Test
    @DisplayName("Test getWorkerAvailableHours fail (null worker)")
    void testGetWorkerAvailableHoursByFailNullWorker() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.empty());
        when(repo.findById(new Hours.HoursPK(worker, LocalDate.of(2020, 10, 15).getDayOfWeek()))).thenReturn(Optional.of(hours.get(0)));

        mockMvc.perform(get("/api/hours/available/1").param("date", "2020-10-15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test getWorkerAvailableHours fail (null hours list)")
    void testGetWorkerAvailableHoursByFailNullHoursList() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.findById(new Hours.HoursPK(worker, LocalDate.of(2020, 10, 15).getDayOfWeek()))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/hours/available/1").param("date", "2020-10-15").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewHours success")
    void testCreateNewHoursSuccess() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.of(worker));
        when(repo.save(any())).thenReturn(hours.get(0));
        String inputJson = "{\n" +
                "        \"id\": {\n" +
                "            \"dayOfWeek\": 1\n" +
                "        },\n" +
                "        \"start\": \"09:00\",\n" +
                "        \"end\": \"17:00\"\n" +
                "    }";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/hours/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.err.println(response.getContentAsString());
        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test createNewHours badRequest")
    void testCreateNewUserFail() throws Exception {
        // Mocking service
        when(workerRepo.findById(1L)).thenReturn(Optional.empty());
        when(repo.save(any())).thenReturn(hours.get(0));
        String inputJson = "{\n" +
                "        \"id\": {\n" +
                "            \"dayOfWeek\": 1\n" +
                "        },\n" +
                "        \"start\": null,\n" +
                "        \"end\": null\n" +
                "    }";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/hours/1")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }
}