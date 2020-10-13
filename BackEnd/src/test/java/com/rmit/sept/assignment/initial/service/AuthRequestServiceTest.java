package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.security.JwtAuthUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthRequestServiceTest {
    @Autowired
    private AuthRequestService service;
    @MockBean
    private UserService userService;
    @MockBean
    private WorkerService workerService;
    @MockBean
    private JwtAuthUtils authUtils;

    private static final String USERNAME = "dondon94";
    private static final String TOKEN = "TESTTOKEN";

    @BeforeEach
    void setUp() {
        doReturn(TOKEN).when(authUtils).parseJwt(any());
        doReturn(USERNAME).when(authUtils).getUserNameFromJwtToken(any());
    }


    private User setupUserRequest(Long uid) {
        User u1 = new User(uid, USERNAME, "123Qwe!");
        doReturn(u1).when(userService).findById(u1.getId());
        doReturn(u1).when(userService).findByUsername(u1.getUsername());
        return u1;
    }

    private User setupUserRequest(Long uid, String username) {
        User u1 = new User(uid, username, "123Qwe!");
        doReturn(u1).when(userService).findById(u1.getId());
        doReturn(u1).when(userService).findByUsername(u1.getUsername());
        return u1;
    }

    private Worker setupWorkerRequest(Long uid, Long bid, boolean admin) {
        Worker w1 = new Worker(setupUserRequest(uid));
        w1.setAdmin(admin);
        w1.setBusiness(new Business(bid, "Test Business", "Business for testing"));
        doReturn(w1).when(workerService).findById(w1.getId());
        doReturn(w1).when(workerService).findByUsername(w1.getUser().getUsername());
        return w1;
    }

    private Worker setupWorkerRequest(Long uid, String username, Long bid, boolean admin) {
        Worker w1 = new Worker(setupUserRequest(uid, username));
        w1.setAdmin(admin);
        w1.setBusiness(new Business(bid, "Test Business", "Business for testing"));
        doReturn(w1).when(workerService).findById(w1.getId());
        doReturn(w1).when(workerService).findByUsername(w1.getUser().getUsername());
        return w1;
    }

    @Test
    @DisplayName("Test authUserRequest Success")
    void testAuthUserRequest() {
        User u1 = setupUserRequest(1L);
        assertTrue(service.authUserRequest(TOKEN, u1.getId()));
        assertTrue(service.authUserRequest(TOKEN, u1));
    }

    @Test
    @DisplayName("Test authUserRequest Invalid")
    void testAuthUserRequestInvalid() {
        User u1 = setupUserRequest(1L, "notcorrect");
        assertFalse(service.authUserRequest(TOKEN, u1.getId()));
        assertFalse(service.authUserRequest(TOKEN, u1));
    }

    @Test
    @DisplayName("Test authUserRequest Null")
    void testAuthUserRequestNull() {
        User u1 = setupUserRequest(1L, null);
        assertFalse(service.authUserRequest(TOKEN, u1.getId()));
        assertFalse(service.authUserRequest(TOKEN, u1));
    }

    @Test
    @DisplayName("Test authWorkerRequest Success Same Worker")
    void testAuthWorkerRequestSameWorker() {
        // create a worker with the same username as the session token
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        assertTrue(service.authWorkerRequest(TOKEN, w1.getId()));
        assertTrue(service.authWorkerRequest(TOKEN, w1));
    }

    @Test
    @DisplayName("Test authWorkerRequest Success Different Worker")
    void testAuthWorkerRequestDifferentWorker() {
        // create a worker with a username different to that of the session token
        Worker w1 = setupWorkerRequest(1L, "workeruser", 1L, false);
        assertFalse(service.authWorkerRequest(TOKEN, w1.getId()));
        assertFalse(service.authWorkerRequest(TOKEN, w1));
    }

    @Test
    @DisplayName("Test authWorkerRequest Success Same Business")
    void testAuthWorkerRequestSameBusiness() {
        // create a worker
        Worker w1 = setupWorkerRequest(2L, "workeruser", 1L, false);
        // create an admin belonging to the same business as the worker
        Worker w2 = setupWorkerRequest(1L, 1L, true);
        // validate that the admin user w2 can edit w1 as they belong to the same business
        assertTrue(service.authWorkerRequest(TOKEN, w1.getId()));
        assertTrue(service.authWorkerRequest(TOKEN, w1));
    }

    @Test
    @DisplayName("Test authWorkerRequest Different Business")
    void testAuthWorkerRequestDifferentBusiness() {
        // create a worker
        Worker w1 = setupWorkerRequest(2L, "workeruser", 1L, false);
        // create an admin belonging to a DIFFERENT business as the worker
        Worker w2 = setupWorkerRequest(1L, 2L, true);
        // validate that the admin user w2 CANNOT edit w1 as they belong to DIFFERENT businesses
        assertFalse(service.authWorkerRequest(TOKEN, w1.getId()));
        assertFalse(service.authWorkerRequest(TOKEN, w1));
    }

    @Test
    @DisplayName("Test authWorkerRequest Not Admin")
    void testAuthWorkerRequestNotAdmin() {
        // create a worker
        Worker w1 = setupWorkerRequest(2L, "workeruser", 1L, false);
        // create a non-admin belonging to the same business as the worker
        Worker w2 = setupWorkerRequest(1L, 1L, false);
        // validate that the user w2 CANNOT edit w1 as w2 is not an admin
        assertFalse(service.authWorkerRequest(TOKEN, w1.getId()));
        assertFalse(service.authWorkerRequest(TOKEN, w1));
    }

    @Test
    @DisplayName("Test authCreateBusinessRequest Success")
    void testAuthCreateBusinessRequest() {
        // create an admin user
        Worker w1 = setupWorkerRequest(1L, 1L, true);
        // validate that the admin user can create a new business
        assertTrue(service.authCreateBusinessRequest(TOKEN));
    }

    @Test
    @DisplayName("Test authCreateBusinessRequest Not Admin")
    void testAuthCreateBusinessRequestNotAdmin() {
        // create an non-admin user
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        // validate that the user cannot create a new business
        assertFalse(service.authCreateBusinessRequest(TOKEN));
    }

    @Test
    @DisplayName("Test authCreateBusinessRequest Not Found")
    void testAuthCreateBusinessRequestNotFound() {
        // create an non-admin user
        Worker w1 = setupWorkerRequest(1L, 1L, true);
        // mock workerService returning null when searching by username
        doReturn(null).when(workerService).findByUsername(w1.getUser().getUsername());
        // validate that the user cannot create a new business as they are invalid
        assertFalse(service.authCreateBusinessRequest(TOKEN));
    }

    @Test
    @DisplayName("Test authUpdateBusinessRequest Success")
    void testAuthUpdateBusinessRequest() {
        // create a new admin user for business 1L
        Worker w1 = setupWorkerRequest(1L, 1L, true);
        // validate that they are allowed to edit the business they belong to
        assertTrue(service.authUpdateBusinessRequest(TOKEN, 1L));
    }

    @Test
    @DisplayName("Test authUpdateBusinessRequest Not Admin")
    void testAuthUpdateBusinessRequestNotAdmin() {
        // create a new non-admin user for business 1L
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        // validate that they are not allowed to edit the business they belong to
        assertFalse(service.authUpdateBusinessRequest(TOKEN, 1L));
    }

    @Test
    @DisplayName("Test authUpdateBusinessRequest Different Business")
    void testAuthUpdateBusinessRequestDifferentBusiness() {
        // create a new non-admin user for business 1L
        Worker w1 = setupWorkerRequest(1L, 1L, true);
        // validate that they are not allowed to edit the business they belong to
        assertFalse(service.authUpdateBusinessRequest(TOKEN, 2L));
    }

    @Test
    @DisplayName("Test authUpdateBusinessRequest Null")
    void testAuthUpdateBusinessRequestNull() {
        // create a new non-admin user for business 1L
        Worker w1 = setupWorkerRequest(1L, 1L, true);
        // validate that they are not allowed to edit the business they belong to
        assertFalse(service.authUpdateBusinessRequest(TOKEN, null));
    }

    @Test
    @DisplayName("Test authGetBusinessEntitiesRequest Success")
    void testAuthGetBusinessEntitiesRequest() {
        // create new worker
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        // validate that they can get bookings pertaining to their business
        assertTrue(service.authGetBusinessEntitiesRequest(TOKEN, 1L));
        // create new admin worker
        w1 = setupWorkerRequest(1L, 1L, true);
        // validate that they can get bookings pertaining to their business
        assertTrue(service.authGetBusinessEntitiesRequest(TOKEN, 1L));
    }

    @Test
    @DisplayName("Test authGetBusinessEntitiesRequest Different Business")
    void testAuthGetBusinessEntitiesRequestDifferentBusiness() {
        // create new worker
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        // validate that they can get bookings pertaining to their business
        assertFalse(service.authGetBusinessEntitiesRequest(TOKEN, 2L));
    }

    @Test
    @DisplayName("Test authGetBusinessEntitiesRequest Null")
    void testAuthGetBusinessEntitiesRequestNull() {
        // create new worker
        Worker w1 = setupWorkerRequest(1L, 1L, false);
        // validate that they can get bookings pertaining to their business
        assertFalse(service.authGetBusinessEntitiesRequest(TOKEN, null));
    }
}