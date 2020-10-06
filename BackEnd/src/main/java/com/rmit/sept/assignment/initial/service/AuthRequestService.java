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
        String username = usernameFromToken(token);
        if (username == null || user == null || user.getUsername() == null)
            return false;
        return username.compareTo(user.getUsername()) == 0;
    }

    public boolean authWorkerRequest(String token, Worker worker) {
        String username = usernameFromToken(token);
        if (worker == null)
            return false;
        Long workerId = worker.getId();
        if (workerId == null)
            return false;
        User user = userService.findById(workerId);
        if (user == null)
            return false;
        if (username.compareTo(user.getUsername()) == 0) {
            return true;
        } else {
            Business business = worker.getBusiness();
            if (business == null || business.getId() == null)
                return false;
            Worker temp = workerService.findByUsername(username);
            if (temp != null) {
                return temp.getBusiness().getId().equals(worker.getBusiness().getId());
            }
        }
        return false;
    }

    public boolean authBusinessRequest(String token, Worker worker) {
        String username = usernameFromToken(token);
        return false;
    }

    public boolean authHoursRequest(String token, Worker worker) {
        String username = usernameFromToken(token);
        return false;
    }

    public boolean authBookingRequest(String token, User user, Worker worker) {
        String username = usernameFromToken(token);
        return false;
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
