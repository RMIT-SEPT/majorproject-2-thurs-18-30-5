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

    public boolean authUserRequest(String token, Long userId) {
        try {
            String username = usernameFromToken(token);
            User user = userService.findByUsername(username);
            return user.getId().equals(userId);
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean authWorkerRequest(String token, Worker worker) {
        if (worker == null) return false;
        String username = usernameFromToken(token);
        return (sameWorker(username, worker) || sameBusiness(username, worker));
    }

    public boolean authWorkerRequest(String token, Long workerId) {
        if (workerId == null) return false;
        String username = usernameFromToken(token);
        Worker worker = workerService.findById(workerId);
        return (sameWorker(username, worker) || sameBusiness(username, worker));
    }

    private boolean sameWorker(String username, Worker worker) {
        try {
            Long workerId = worker.getId();
            User user = userService.findById(workerId);
            return (username.compareTo(user.getUsername()) == 0);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean sameBusiness(String username, Worker worker) {
        try {
            Business business = worker.getBusiness();
            Worker temp = workerService.findByUsername(username);
            return temp.getBusiness().getId().equals(business.getId());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean authCreateBusinessRequest(String token) {
        String username = usernameFromToken(token);
        Worker worker = workerService.findByUsername(username);
        if (worker == null) return false;
        return worker.getAdmin();
    }

    public boolean authUpdateBusinessRequest(String token, Long businessId) {
        String username = usernameFromToken(token);
        Worker worker = workerService.findByUsername(username);
        if (worker == null || !worker.getAdmin()) return false;
        Business business = worker.getBusiness();
        if (business == null) return false;
        return (business.getId().equals(businessId));
    }

    public boolean authGetBusinessEntitiesRequest(String token, Long businessId) {
        String username = usernameFromToken(token);
        Worker worker = workerService.findByUsername(username);
        if (worker == null) return false;
        Business business = worker.getBusiness();
        if (business == null) return false;
        return (business.getId().equals(businessId));
    }

    public boolean authBookingRequest(String token, User user, Worker worker) {
        return (authUserRequest(token, user) || authWorkerRequest(token, worker));
    }

    public boolean authBookingRequest(String token, Long userId, Long workerId) {
        User user = userService.findById(userId);
        Worker worker = workerService.findById(workerId);
        return (authUserRequest(token, user) || authWorkerRequest(token, worker));
    }

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

    private String usernameFromToken(String token) {
        return jwtAuthUtils.getUserNameFromJwtToken(jwtAuthUtils.parseJwt(token));
    }
}
