package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.*;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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

//    @MockBean
//    JwtAuthUtils authUtils;

    private List<Booking> bookings;
    private Booking b1;
    private Booking b2;
    private String booking;


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

        booking = "{\n" +
                "        \"id\": 1,\n" +
                "        \"worker\": {\n" +
                "            \"id\": 1,\n" +
                "            \"user\": {\n" +
                "                \"id\": 1,\n" +
                "                \"username\": \"dondon94\",\n" +
                "                \"password\": \"$2a$10$g5tIuKcFHoI5AiJC7Ce.EeTW30bEzRBQtcJCZs065ar9ME27MwG/m\",\n" +
                "                \"firstName\": \"Mike\",\n" +
                "                \"lastName\": null,\n" +
                "                \"address\": \"Unit 2, 66 Para Road\",\n" +
                "                \"bookings\": [],\n" +
                "                \"createdAt\": null,\n" +
                "                \"updatedAt\": \"2020-56-10 03:56\",\n" +
                "                \"enabled\": true,\n" +
                "                \"authorities\": null,\n" +
                "                \"accountNonExpired\": true,\n" +
                "                \"credentialsNonExpired\": true,\n" +
                "                \"accountNonLocked\": true\n" +
                "            },\n" +
                "            \"business\": {\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Pandemic Hair\",\n" +
                "                \"description\": \"We cut hair good!\",\n" +
                "                \"createdAt\": null,\n" +
                "                \"updatedAt\": \"2020-51-10 03:51\"\n" +
                "            },\n" +
                "            \"createdAt\": null,\n" +
                "            \"updatedAt\": \"2020-09-16 06:09\",\n" +
                "            \"admin\": false\n" +
                "        },\n" +
                "        \"user\": {\n" +
                "            \"id\": 11,\n" +
                "            \"username\": \"customer\",\n" +
                "            \"password\": \"$2a$10$ox/hO4zgNuQqviIZDFScTODHrz/6qw.eXH54ZuAdsNxrbSSuLtAzO\",\n" +
                "            \"firstName\": \"Ali\",\n" +
                "            \"lastName\": \"Khosravi\",\n" +
                "            \"address\": \"Unit 2, 66 Para Road\",\n" +
                "            \"createdAt\": null,\n" +
                "            \"updatedAt\": \"2020-25-10 03:25\",\n" +
                "            \"enabled\": true,\n" +
                "            \"authorities\": null,\n" +
                "            \"accountNonExpired\": true,\n" +
                "            \"credentialsNonExpired\": true,\n" +
                "            \"accountNonLocked\": true\n" +
                "        },\n" +
                "        \"start\": \"2020-10-11 11:00\",\n" +
                "        \"end\": \"2020-10-11 12:00\",\n" +
                "        \"status\": \"COMPLETED\",\n" +
                "        \"createdAt\": null,\n" +
                "        \"updatedAt\": \"2020-10-04 13:00\"\n" +
                "    }";

        when(authRequestService.authBookingRequest(any(), (User) any(), (Worker) any())).thenReturn(true);
        when(authRequestService.authWorkerRequest(any(), (Long) any())).thenReturn(true);
        when(authRequestService.authGetBusinessEntitiesRequest(any(), (Long) any())).thenReturn(true);
        when(authRequestService.authUserRequest(any(), (Long) any())).thenReturn(true);
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
    @DisplayName("Test getBookingByID success")
    void testGetBookingSuccess() throws Exception {
        when(bookingService.findById(1L)).thenReturn(b1);

        mockMvc.perform(get("/api/booking/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test getBookingByID fail")
    void testGetBookingFail() throws Exception {
        when(bookingService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/api/booking/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByWorker success")
    void testGetBookingByWorkerSuccess() throws Exception {
        when(bookingService.findByWorker(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/worker/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByWorker fail")
    void testGetBookingByWorkerFail() throws Exception {
        when(bookingService.findByWorker(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/worker/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByBusiness success")
    void testGetBookingByBusinessSuccess() throws Exception {
        when(bookingService.findByBusiness(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/business/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByBusiness fail")
    void testGetBookingByBusinessFail() throws Exception {
        when(bookingService.findByBusiness(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/business/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByUser success")
    void testGetBookingByUserSuccess() throws Exception {
        when(bookingService.findByUser(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/user/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByUser fail")
    void testGetBookingByUserFail() throws Exception {
        when(bookingService.findByUser(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/user/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createBooking success")
    void testCreateBookingSuccess() throws Exception {
        when(bookingService.saveOrUpdateBooking(ArgumentMatchers.any(Booking.class), ArgumentMatchers.anyBoolean())).thenReturn(bookings.get(0));

        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test createBooking fail")
    void testCreateBookingFail() throws Exception {
        when(bookingService.saveOrUpdateBooking(ArgumentMatchers.any(Booking.class), ArgumentMatchers.anyBoolean())).thenReturn(null);

        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test updateBooking success")
    void testUpdateBookingSuccess() throws Exception {
        when(bookingService.saveOrUpdateBooking(ArgumentMatchers.any(Booking.class), ArgumentMatchers.anyBoolean())).thenReturn(bookings.get(0));
        when(bookingService.findById(any())).thenReturn(bookings.get(0));

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test updateBooking fail")
    void testUpdateBookingFail() throws Exception {
        when(bookingService.saveOrUpdateBooking(ArgumentMatchers.any(Booking.class), ArgumentMatchers.anyBoolean())).thenReturn(null);
        when(bookingService.findById(any())).thenReturn(null);

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
