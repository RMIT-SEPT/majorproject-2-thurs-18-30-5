package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.*;
import com.rmit.sept.assignment.initial.service.BookingService;
import com.rmit.sept.assignment.initial.service.BusinessService;
import com.rmit.sept.assignment.initial.service.HoursService;
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
public class BookingControllerTest {

    MockMvc mockMvc;

    @Autowired
    BookingController bookingController;

    @MockBean
    BookingService bookingService;

    private List<Booking> bookings;

    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.bookingController).build();// Standalone context

        bookings = new ArrayList<Booking>();

        Booking b1 = new Booking(1L);
        bookings.add(b1);

        Booking b2 = new Booking(2L);
        bookings.add(b2);
    }

    @Test
    @DisplayName("Test getBookings")
    void testGetBookings() throws Exception {
        // Mocking service
        when(bookingService.findAll()).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }
}
