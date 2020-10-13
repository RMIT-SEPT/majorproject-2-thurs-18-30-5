package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.model.Hours;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.BusinessService;
import com.rmit.sept.assignment.initial.service.HoursService;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.service.WorkerService;
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

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class BusinessControllerTest {

    MockMvc mockMvc;

    @Autowired
    BusinessController businessController;

    @MockBean
    BusinessService businessService;

    @MockBean
    AuthRequestService authRequestService;

    private List<Business> businesses, businessesByName;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.businessController).build();// Standalone context

        businesses = new ArrayList<Business>();

        Business b1 = new Business("business #1", "description for business #1");
        businesses.add(b1);

        Business b2 = new Business("business #2", "description for business #2");
        businesses.add(b2);

        businessesByName = new ArrayList<Business>();
        businessesByName.add(b1);

        when(authRequestService.authGetBusinessEntitiesRequest(any(), any())).thenReturn(true);
        when(authRequestService.authUpdateBusinessRequest(any(), any())).thenReturn(true);
        when(authRequestService.authCreateBusinessRequest(any())).thenReturn(true);
    }

    @Test
    @DisplayName("Test getBusinesses")
    void testGetBusinesses() throws Exception {
        // Mocking service
        when(businessService.findAll()).thenReturn(businesses);

        mockMvc.perform(get("/api/business/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("business #1")))
                .andExpect(jsonPath("$[1].name", is("business #2")));
    }

    @Test
    @DisplayName("Test getBusiness success")
    void testGetBusinessSuccess() throws Exception {
        // Mocking service
        when(businessService.findById(1L)).thenReturn(businesses.get(0));

        mockMvc.perform(get("/api/business/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("business #1")));
    }

    @Test
    @DisplayName("Test getBusiness not found")
    void testGetBusinessFail() throws Exception {
        // Mocking service
        when(businessService.findById(3L)).thenReturn(null);

        mockMvc.perform(get("/api/business/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBusinessByName success")
    void testGetBusinessByNameSuccess() throws Exception {
        // Mocking service
        when(businessService.findByName("business1")).thenReturn(businessesByName);

        mockMvc.perform(get("/api/business/name/business1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("business #1")));
    }

    @Test
    @DisplayName("Test getBusinessByName not found")
    void testGetBusinessByNameFail() throws Exception {
        // Mocking service
        when(businessService.findByName("RandomName")).thenReturn(new ArrayList<Business>());

        mockMvc.perform(get("/api/business/name/RandomName").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
