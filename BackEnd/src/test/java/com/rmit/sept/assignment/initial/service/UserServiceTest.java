package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.Repositories.UserRepository;
import com.rmit.sept.assignment.initial.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repo;

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(1L);

        User u2 = service.findById(1L);

        assertNotNull(u2);
        assertSame(u1, u2);
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(1L);

        User u2 = service.findById(2L);

        assertNull(u2);
    }

    @Test
    @DisplayName("Test findById Null")
    void testFindByIdNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(1L);

        User u2 = service.findById(null);

        assertNull(u2);
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        User u2 = new User(2L, "samsam98", "123Qwe!");
        User u3 = new User(3L, "mary123", "123Qwe!");

        doReturn(Arrays.asList(u1, u2, u3)).when(repo).findAll();

        Collection<User> users = service.findAll();
        assertEquals(users.size(), 3);

        doReturn(Collections.emptyList()).when(repo).findAll();

        users = service.findAll();
        assertEquals(users.size(), 0);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Create Success")
    void testCreateUser() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1);

        assertNotNull(u2);
        assertEquals(u2, u1);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Create Null")
    void testCreateUserBadRequest() {
        User u1 = new User();
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1);

        assertNull(u2);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Success")
    void testUpdateUser() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1);

        // confirm create worked
        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setUsername("dootdoot94");
//        doReturn(Optional.of(c1)).when(repo).findById(any());

        User c3 = service.saveOrUpdateUser(u1);

        // confirm that update username worked
        assertNotNull(c3);
        assertEquals("dootdoot94", c3.getUsername());
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update by username Success")
    void testUpdateUserByUsername() {
        User u1 = new User();
        u1.setUsername("dondon94");
        u1.setPassword("123Qwe!");

        doReturn(u1).when(repo).save(u1);

        User u2 = service.saveOrUpdateUser(u1);

        // confirm create worked
        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setFirstName("Donald");

        User u3 = service.saveOrUpdateUser(u1);


        assertNotNull(u3);
        assertEquals("Donald", u3.getFirstName());
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Null")
    void testUpdateUserNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1);

        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setUsername(null);
        u1.setId(null);
//        doReturn(Optional.of(c1)).when(repo).findById(any());

        User c3 = service.saveOrUpdateUser(u1);

        assertNull(c3);
    }
}