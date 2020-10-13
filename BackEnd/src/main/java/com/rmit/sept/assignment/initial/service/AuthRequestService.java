package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.model.Business;
import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.model.Worker;
import com.rmit.sept.assignment.initial.security.JwtAuthUtils;
import com.rmit.sept.assignment.initial.security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthRequestService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private WorkerService workerService;
    @Autowired
    private JwtAuthUtils jwtAuthUtils;

    /**
     * Authenticate a user request - used to check if the user updating the user record is the same as the one noted
     * in the session token. A user entity is only be able to edited by that user.
     * @param token: token value passed to endpoint
     * @param user: user being edited
     * @return true if valid, otherwise false
     */
    public boolean authUserRequest(String token, User user) {
        try {
            String username = usernameFromToken(token);
            String userUsername = user.getUsername();
            if (userUsername == null) {
                Long userId = user.getId();
                userUsername = userService.findById(userId).getUsername();
            }
            return username.compareTo(userUsername) == 0;
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Overloaded authUserRequest, allowing for passing userId of User entity being edited
     * @param token: token value passed to endpoint
     * @param userId: id of user being edited
     * @return true if valid, otherwise false
     */
    public boolean authUserRequest(String token, Long userId) {
        try {
            String username = usernameFromToken(token);
            User user = userService.findByUsername(username);
            return user.getId().equals(userId);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Authenticate a Worker Request - used to check if the user updating the Worker record is the same as the one noted
     * in the session token. A worker entity is only able to be edited by that worker or an admin belonging to the same
     * business.
     * @param token: session token passed to endpoint
     * @param worker: worker entity being edited
     * @return true if valid, otherwise false
     */
    public boolean authWorkerRequest(String token, Worker worker) {
        if (worker == null) return false;
        String username = usernameFromToken(token);
        return (sameWorker(username, worker) || sameBusiness(username, worker));
    }

    /**
     * Overloaded authWorkerRequest to allow for passing id of Worker being edited
     * @param token: session token passed to controller endpoint
     * @param workerId: id of worker entity being edited
     * @return true if valid, otherwise false
     */
    public boolean authWorkerRequest(String token, Long workerId) {
        try {
            Worker worker = workerService.findById(workerId);
            return (authWorkerRequest(token, worker));
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Validation method to check if the username param and username of the worker match. This is used when establishing
     * if a Worker being edited is the same as the worker noted in the session token
     * @param username: username from session token
     * @param worker: worker entity being edited
     * @return true if valid, otherwise false
     */
    private boolean sameWorker(String username, Worker worker) {
        try {
            Long workerId = worker.getId();
            User user = userService.findById(workerId);
            return (username.compareTo(user.getUsername()) == 0);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Validation method to check if the username from the session token belongs to a admin worker who belongs to the
     * same business as the Worker entity being edited.
     * @param username: username from session token
     * @param worker: Worker entity being edited
     * @return true if valid, otherwise false
     */
    private boolean sameBusiness(String username, Worker worker) {
        try {
            Worker temp = workerService.findByUsername(username);
            return temp.getBusiness().getId().equals(worker.getBusiness().getId()) && temp.getAdmin();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Authenticates a Business Creation request - only Admin Workers should be able to create businesses. If the token
     * belongs to an admin user then the request is valid, otherwise it will return false.
     * @param token: session token value passed to endpoint
     * @return true if valid, otherwise false
     */
    public boolean authCreateBusinessRequest(String token) {
        try {
            String username = usernameFromToken(token);
            Worker worker = workerService.findByUsername(username);
            if (worker == null) return false;
            return worker.getAdmin();
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Authenticate an edit Business request - only Admin Workers belonging to that business should be able to edit
     * the business record.
     * @param token: session token passed to endpoint
     * @param businessId: id of Business being edited
     * @return true if valid, otherwise false
     */
    public boolean authUpdateBusinessRequest(String token, Long businessId) {
        try {
            String username = usernameFromToken(token);
            Worker worker = workerService.findByUsername(username);
            if (worker == null || !worker.getAdmin()) return false;
            Business business = worker.getBusiness();
            if (business == null) return false;
            return (business.getId().equals(businessId));
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Authenticate requests to get Entities belonging to a business - used for getting a list of all bookings for a
     * business. This functionality should only be accessible to Workers who belong to that business. If the token
     * belongs to a Worker who belongs to the business being queried then the request is valid.
     * @param token: session token passed to the endpoint
     * @param businessId: id of business being queried
     * @return true if valid, otherwise false
     */
    public boolean authGetBusinessEntitiesRequest(String token, Long businessId) {
        try {
            String username = usernameFromToken(token);
            Worker worker = workerService.findByUsername(username);
            if (worker == null) return false;
            Business business = worker.getBusiness();
            if (business == null) return false;
            return (business.getId().equals(businessId));
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Authentication of Booking update requests - this functionality should be available to users to meet either
     * authUserRequest criteria or authWorkerRequest criteria.
     * i.e. the token belongs to the Customer who made the booking, or the Worker who is assigned to the booking, or an
     * Admin worker who belongs to the same business as the worker assigned to the booking.
     * @param token: session token passed to endpoint
     * @param user: User entity from the Booking
     * @param worker: Worker entity from the Booking
     * @return true if one of the conditions held, otherwise false
     */
    public boolean authBookingRequest(String token, User user, Worker worker) {
        return (authUserRequest(token, user) || authWorkerRequest(token, worker));
    }

    /**
     * Overloaded authBookingRequest to allow for Id values for user and worker.
     * @param token: session token passed to endpoint
     * @param userId: id of user attached to the booking
     * @param workerId: id of worker attached to the booking
     * @return true if one of the conditions held, otherwise false
     */
    public boolean authBookingRequest(String token, Long userId, Long workerId) {
        try {
            User user = userService.findById(userId);
            Worker worker = workerService.findById(workerId);
            return authBookingRequest(token, user, worker);
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * Service method used to validate user credentials. This is used when a customer or worker logs in, and returns a
     * session token if the username and password are valid. This session token is then used to authenticate any further
     * api calls.
     * @param username: username of user being authenticated
     * @param password: password of user
     * @return JwtResponse if valid, otherwise returns null
     */
    public JwtResponse getJwtResponse(String username, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtAuthUtils.generateJwtToken(authentication);
            User userDetails = (User) authentication.getPrincipal();
            return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername());
        } catch (BadCredentialsException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Helper method to extract a username from a session token
     * @param token: session token
     * @return username from token parameter
     */
    private String usernameFromToken(String token) {
        return jwtAuthUtils.getUserNameFromJwtToken(jwtAuthUtils.parseJwt(token));
    }
}
