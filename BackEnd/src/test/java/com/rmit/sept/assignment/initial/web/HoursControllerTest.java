package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.service.HoursService;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.WorkerService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class HoursControllerTest {

    MockMvc mockMvc;

    @Autowired
    HoursController hoursController;

    @MockBean
    HoursService hoursService;

    @MockBean
    WorkerService workerService;

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
    }

    @Test
    @DisplayName("Test getWorkersHours success")
    void testGetWorkerHoursSuccess() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(worker);
        when(hoursService.findByWorker(worker)).thenReturn(hours);

        mockMvc.perform(get("/api/hours/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id.dayOfWeek", is(0)))
                .andExpect(jsonPath("$[1].id.dayOfWeek", is(1)));
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null worker)")
    void testGetWorkerHoursFail() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/hours/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkersHours success")
    void testGetWorkerHoursByDaySuccess() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(worker);
        when(hoursService.findById(worker, DayOfWeek.FRIDAY)).thenReturn(hours.get(0));

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id.dayOfWeek", is(0)));
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null worker)")
    void testGetWorkerHoursByDayFailNullWorker() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getWorkersHours fail (null hours)")
    void testGetWorkerHoursByDayFailNullHours() throws Exception {
        // Mocking service
        when(workerService.findById(1L)).thenReturn(worker);
        when(hoursService.findById(worker, DayOfWeek.FRIDAY)).thenReturn(null);

        mockMvc.perform(get("/api/hours/1").param("dayOfWeek", "0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createNewHours success")
    void testCreateNewHoursSuccess() throws Exception {
        // Mocking service
        when(hoursService.saveOrUpdateHours(ArgumentMatchers.any(Hours.class))).thenReturn(hours.get(0));

        String inputJson = "{\n" +
                "        \"id\": {\n" +
                "            \"worker\": {\n" +
                "                \"id\": 1,\n" +
                "                \"user\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"username\": \"ali123\",\n" +
                "                    \"password\": \"123Qwe!\",\n" +
                "                    \"firstName\": null,\n" +
                "                    \"lastName\": null,\n" +
                "                    \"address\": null,\n" +
                "                    \"createdAt\": null,\n" +
                "                    \"updatedAt\": \"2020-14-06 08:14\"\n" +
                "                },\n" +
                "                \"createdAt\": null,\n" +
                "                \"updatedAt\": \"2020-14-06 08:14\",\n" +
                "                \"admin\": false\n" +
                "            },\n" +
                "            \"dayOfWeek\": 1\n" +
                "        },\n" +
                "        \"start\": null,\n" +
                "        \"end\": null\n" +
                "    }";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/hours")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test createNewHours badRequest")
    void testCreateNewUserFail() throws Exception {
        // Mocking service
        when(hoursService.saveOrUpdateHours(ArgumentMatchers.any(Hours.class))).thenReturn(null);

        String inputJson = "{\n" +
                "        \"id\": {\n" +
                "            \"worker\": {\n" +
                "                \"id\": 1,\n" +
                "                \"user\": {\n" +
                "                    \"id\": 1,\n" +
                "                    \"username\": \"ali123\",\n" +
                "                    \"password\": \"123Qwe!\",\n" +
                "                    \"firstName\": null,\n" +
                "                    \"lastName\": null,\n" +
                "                    \"address\": null,\n" +
                "                    \"createdAt\": null,\n" +
                "                    \"updatedAt\": \"2020-14-06 08:14\"\n" +
                "                },\n" +
                "                \"createdAt\": null,\n" +
                "                \"updatedAt\": \"2020-14-06 08:14\",\n" +
                "                \"admin\": false\n" +
                "            },\n" +
                "            \"dayOfWeek\": 1\n" +
                "        },\n" +
                "        \"start\": null,\n" +
                "        \"end\": null\n" +
                "    }";
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/hours")
                .accept(MediaType.APPLICATION_JSON).content(inputJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("Test deleteWorkersHours success")
    void testDeleteWorkerHoursSuccess() throws Exception {
        // Mocking service
        when(hoursService.deleteHours(worker.getId(), DayOfWeek.FRIDAY)).thenReturn(true);

        mockMvc.perform(delete("/api/hours/1").param("dayOfWeek", "0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test deleteWorkersHours fail (null worker)")
    void testDeleteWorkerHoursFailNullWorker() throws Exception {
        // Mocking service
        when(hoursService.deleteHours(null, DayOfWeek.FRIDAY)).thenReturn(false);

        mockMvc.perform(delete("/api/hours/1").param("dayOfWeek", "0").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test deleteWorkersHours fail (null weekDay)")
    void testDeleteWorkerHoursFailNullWeekDay() throws Exception {
        // Mocking service
        when(hoursService.deleteHours(1L, null)).thenReturn(false);

        mockMvc.perform(delete("/api/hours/1").param("dayOfWeek", "null").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}