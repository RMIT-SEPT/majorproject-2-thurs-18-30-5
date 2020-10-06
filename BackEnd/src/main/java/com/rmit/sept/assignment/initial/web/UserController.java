package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.security.JwtAuthUtils;
import com.rmit.sept.assignment.initial.security.JwtResponse;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.plugin.liveconnect.SecurityContextHelper;

import java.util.Collection;

/**
 * User Controller class - allows access/updates on User entities
 */
@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FieldValidationService validationService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAuthUtils jwtAuthUtils;

    /**
     * Returns a list of users
     * @return list of all User objects
     */
    @GetMapping("/all")
    public ResponseEntity<Collection<User>> getUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    /**
     * Return a specific user based on id
     * @param id Long id of user to fetch
     * @return User object with HttpStatus.OK, or null and HttpStatus.NOT_FOUND if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        // if the service return null this means no user was found
        HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(user, status);
    }

    /**
     * Authenticate a user based on username and password
     * @param username: username of user
     * @param password: password of user
     * @return User object of authentication was successful
     */
    @PostMapping("/auth/{username}")
    public ResponseEntity<?> authenticateUser(@PathVariable String username, @RequestParam String password) {
        if (username != null && password != null) {
//            User user = userService.authenticateUser(username, password);
//            HttpStatus status = user != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            System.err.println(authentication.toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtAuthUtils.generateJwtToken(authentication);

            User userDetails = (User) authentication.getPrincipal();
            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername()));
        } else {
            return new ResponseEntity<>("Invalid request", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint to create a new worker
     * @param user user to create
     * @param result user parameter errors based on field restrictions
     * @return User object and HttpStatus.CREATED if successful, otherwise null and HttpStatus.BAD_REQUEST
     */
    @PostMapping("")
    public ResponseEntity<?> createNewUser(@Validated @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            User user1 = userService.saveOrUpdateUser(user, true);
            HttpStatus status = (user1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<User>(user1, status);
        } else {
            return errors;
        }
    }

    /**
     * Endpoint to update an existing user
     * @param user: User object with updated details, and current password (plaintext)
     * @param result: field errors/validation based on User entity
     * @return ResponseEntity with updated User object and corresponding status code (CREATED, BAD_REQUEST)
     */
    @PutMapping("")
    public ResponseEntity<?> updateUser(@Validated @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            if (userService.authenticateUser(user.getId(), user.getPassword()) != null) {
                User user1 = userService.saveOrUpdateUser(user, false);
                HttpStatus status = (user1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
                return new ResponseEntity<User>(user1, status);
            }
            return new ResponseEntity<>("Invalid User Credentials", HttpStatus.NOT_FOUND);
        } else {
            return errors;
        }
    }
}
