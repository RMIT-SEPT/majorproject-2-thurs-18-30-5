package com.rmit.sept.assignment.initial.integration;

import com.rmit.sept.assignment.initial.model.*;
import com.rmit.sept.assignment.initial.repositories.BookingRepository;
import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.repositories.WorkerRepository;
import com.rmit.sept.assignment.initial.service.AuthRequestService;
import com.rmit.sept.assignment.initial.service.BookingService;
import com.rmit.sept.assignment.initial.web.BookingController;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.mockito.Mockito.when;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
public class BookingIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    BookingController bookingController;

    @Autowired
    BookingService bookingService;

    @MockBean
    AuthRequestService authRequestService;

    @MockBean
    BookingRepository repo;
    @MockBean
    WorkerRepository workerRepo;
    @MockBean
    UserRepository userRepo;

//    @MockBean
//    JwtAuthUtils authUtils;

    private List<Booking> bookings;
    private Booking b1;
    private Booking b2;
    private Worker w1;
    private User u1;
    private String booking;


    @BeforeEach
    public void setup() {
        this.mockMvc = standaloneSetup(this.bookingController).build();// Standalone context

        bookings = new ArrayList<Booking>();

        b1 = new Booking(1L);
        u1 = new User(1L, "customer", "123Qwe!");
        b1.setUser(u1);
        w1 = new Worker(new User(2L, "worker", "123Qwe!"));
        b1.setWorker(w1);
        bookings.add(b1);

        b2 = new Booking(2L);
        bookings.add(b2);

        booking = "{\n" +
                "        \"id\": 1,\n" +
                "        \"worker\": {\n" +
                "            \"id\": 2,\n" +
                "            \"user\": {\n" +
                "                \"id\": 2,\n" +
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
                "            \"id\": 1,\n" +
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
        when(repo.findAll()).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByID success")
    void testGetBookingSuccess() throws Exception {
        when(repo.findById(1L)).thenReturn(Optional.of(b1));

        mockMvc.perform(get("/api/booking/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test getBookingByID fail")
    void testGetBookingFail() throws Exception {
        when(repo.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/booking/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByWorker success")
    void testGetBookingByWorkerSuccess() throws Exception {
        when(repo.findAllByWorker_Id(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/worker/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByWorker fail")
    void testGetBookingByWorkerFail() throws Exception {
        when(repo.findAllByWorker_Id(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/worker/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByBusiness success")
    void testGetBookingByBusinessSuccess() throws Exception {
        when(repo.findAllByWorker_Business_Id(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/business/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByBusiness fail")
    void testGetBookingByBusinessFail() throws Exception {
        when(repo.findAllByWorker_Business_Id(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/business/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test getBookingByUser success")
    void testGetBookingByUserSuccess() throws Exception {
        when(repo.findAllByUser_Id(1L)).thenReturn(bookings);

        mockMvc.perform(get("/api/booking/all/user/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    @DisplayName("Test getBookingByUser fail")
    void testGetBookingByUserFail() throws Exception {
        when(repo.findAllByUser_Id(1L)).thenReturn(new ArrayList<Booking>());

        mockMvc.perform(get("/api/booking/all/user/1").header("Authorization", "token").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test createBooking success")
    void testCreateBookingSuccess() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test createBooking fail")
    void testCreateBookingFail() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.empty()); // invalid user id
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.empty());  // invalid worker id
        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));
        when(workerRepo.findById(u1.getId())).thenReturn(Optional.of(w1));  // user is worker - booking invalid
        mockMvc.perform(post("/api/booking").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test updateBooking success")
    void testUpdateBookingSuccess() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(repo.findById(1L)).thenReturn(Optional.of(bookings.get(0)));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)));
    }

    @Test
    @DisplayName("Test updateBooking fail - invalid user")
    void testUpdateBookingFailUser() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(repo.findById(1L)).thenReturn(Optional.of(bookings.get(0)));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.empty());
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test updateBooking fail - invalid worker")
    void testUpdateBookingFailWorker() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(repo.findById(1L)).thenReturn(Optional.of(bookings.get(0)));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test updateBooking fail - invalid booking")
    void testUpdateBookingFailBooking() throws Exception {
        when(repo.save(any())).thenReturn(bookings.get(0));
        when(repo.findById(1L)).thenReturn(Optional.empty());
        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updateBooking fail - invalid save")
    void testUpdateBookingFailSave() throws Exception {
        when(repo.save(any())).thenReturn(null);
        when(repo.findById(1L)).thenReturn(Optional.of(bookings.get(0)));
        when(userRepo.findById(u1.getId())).thenReturn(Optional.of(u1));
        when(workerRepo.findById(w1.getId())).thenReturn(Optional.of(w1));

        mockMvc.perform(put("/api/booking/1").header("Authorization", "token").content(booking).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
