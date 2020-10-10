package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.*;
import com.rmit.sept.assignment.initial.security.JwtAuthUtils;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.BookingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

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

    @MockBean
    AuthRequestService authRequestService;

    @MockBean
    JwtAuthUtils authUtils;

    private List<Booking> bookings;
    private Booking b1;
    private Booking b2;



    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.bookingController).build();// Standalone context

        bookings = new ArrayList<Booking>();

        b1 = new Booking(1L);
        b1.setUser(new User(1L, "customer", "123Qwe!"));
        b1.setWorker(new Worker(new User(2L, "worker", "123Qwe!")));
        bookings.add(b1);

        b2 = new Booking(2L);
        bookings.add(b2);

        when(authUtils.validateJwtToken(any())).thenReturn(true);
        when(authRequestService.authBookingRequest(any(), (User) any(), (Worker) any())).thenReturn(true);
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

    @Test
    @DisplayName("Test getBookingByID")
    void testGetBooking() throws Exception {
        when(bookingService.findById(1L)).thenReturn(b1);

        mockMvc.perform(get("/api/booking/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }
}
