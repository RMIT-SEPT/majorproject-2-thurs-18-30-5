package com.rmit.sept.assignment.initial.web;

import com.rmit.sept.assignment.initial.model.User;
import com.rmit.sept.assignment.initial.service.FieldValidationService;
import com.rmit.sept.assignment.initial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/customer")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private FieldValidationService validationService;

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
     * Endpoint to create a new worker
     * @param user user to create
     * @param result user parameter errors based on field restrictions
     * @return User object and HttpStatus.CREATED if successful, otherwise null and HttpStatus.BAD_REQUEST
     */
    @PostMapping("")
    public ResponseEntity<?> createNewUser(@Validated @RequestBody User user, BindingResult result) {
        ResponseEntity<?> errors = validationService.mapFieldErrors(result);
        if (errors == null) {
            User user1 = userService.saveOrUpdateUser(user);
            HttpStatus status = (user1 == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
            return new ResponseEntity<User>(user1, status);
        } else {
            return errors;
        }
    }
}
