package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.UserRepository;
import com.rmit.sept.assignment.initial.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repo;

    @MockBean
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setup() {
        // ensure that password will match - override when need to
        doReturn(true).when(encoder).matches(any(), any());
    }

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

        User u2 = service.findById(null);  // pass null value as ID

        assertNull(u2);
    }

    @Test
    @DisplayName("Test findByUsername Success")
    void testFindByUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        User u2 = service.findByUsername(u1.getUsername());

        assertNotNull(u2);
        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("Test findByUsername Not Found")
    void testFindByUsernameNotFound() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.empty()).when(repo).findByUsername(u1.getUsername());

        User u2 = service.findByUsername(u1.getUsername());

        assertNull(u2);
    }

    @Test
    @DisplayName("Test findByUsername Null")
    void testFindByUsernameNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.empty()).when(repo).findByUsername(u1.getUsername());

        User u2 = service.findByUsername(null);

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authUsername Success")
    void testAuthUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        User u2 = service.authenticateUser(u1.getUsername(), u1.getPassword());

        assertNotNull(u2);
        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("Test authUsername Username Null")
    void testAuthUsernameUsernameNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        User u2 = service.authenticateUser((String) null, u1.getPassword());

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authUsername Password Null")
    void testAuthUsernamePasswordNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        User u2 = service.authenticateUser(u1.getUsername(), null);

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authUsername Not Found")
    void testAuthUsernameNotFound() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        User u2 = service.authenticateUser("fakeusername", u1.getPassword());

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authId Success")
    void testAuthId() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(u1.getId());

        User u2 = service.authenticateUser(u1.getId(), u1.getPassword());

        assertNotNull(u2);
        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("Test authId Id Null")
    void testAuthIdIdNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(u1.getId());

        User u2 = service.authenticateUser((Long) null, u1.getPassword());  // missing user id

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authId Password Null")
    void testAuthIdPasswordNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(u1.getId());

        User u2 = service.authenticateUser(u1.getId(), null);  // missing password

        assertNull(u2);
    }

    @Test
    @DisplayName("Test authId Not Found")
    void testAuthIdNotFound() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findById(u1.getId());

        User u2 = service.authenticateUser(2L, u1.getPassword());  // invalid Id

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

        User u2 = service.saveOrUpdateUser(u1, true);

        assertNotNull(u2);
        assertEquals(u2, u1);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Create Null")
    void testCreateUserBadRequest() {
        User u1 = new User();
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1, true);  // user has null ID

        assertNull(u2);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Create Null Password")
    void testCreateUserNullPassword() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        u1.setPassword(null);
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1, true);  // user has null ID

        assertNull(u2);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Username in use")
    void testCreateUserDupeUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(any());
        User u2 = new User(2L, "dondon94", "123Qwe!"); // duplicate username
        doReturn(u2).when(repo).save(any());

        User u3 = service.saveOrUpdateUser(u2, true);
        assertNull(u3);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Success")
    void testUpdateUser() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1, true);

        // confirm create worked
        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setUsername("dootdoot94");
        u1.setPassword("123Qwe!");
        User c3 = service.saveOrUpdateUser(u1, false);

        // confirm that update username worked
        assertNotNull(c3);
        assertEquals("dootdoot94", c3.getUsername());
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update by username Success")
    void testUpdateUserByUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");

        doReturn(u1).when(repo).save(u1);

        User u2 = service.saveOrUpdateUser(u1, true);

        // confirm create worked
        assertNotNull(u2);
        assertEquals(u2, u1);

        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());

        u1.setFirstName("Donald");
        u1.setPassword("123Qwe!");
        User u3 = service.saveOrUpdateUser(u1, false);

        assertNotNull(u3);
        assertEquals("Donald", u3.getFirstName());
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Username Null")
    void testUpdateUserUsernameNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1, true);

        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setUsername(null);

        User c3 = service.saveOrUpdateUser(u1, false);  // user has a null id - should not update

        assertNull(c3);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Id Null")
    void testUpdateUserIdNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(u1).when(repo).save(any());

        User u2 = service.saveOrUpdateUser(u1, true);

        assertNotNull(u2);
        assertEquals(u2, u1);

        u1.setId(null);

        User c3 = service.saveOrUpdateUser(u1, false);  // user has a null id - should not update

        assertNull(c3);
    }

    @Test
    @DisplayName("Test saveOrUpdateUser Update Username in use")
    void testUpdateUserDupeUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());
        User u2 = new User(2L, "dondon94", "123Qwe!");  // duplicate username
        doReturn(u2).when(repo).save(any());

        User u3 = service.saveOrUpdateUser(u2, false);  // test updating with duplicate username

        assertNull(u3);
    }

    @Test
    @DisplayName("Test loadUserByUsername Success")
    void testLoadUserByUsername() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());
        UserDetails user = service.loadUserByUsername(u1.getUsername());
        assertNotNull(user);
        assertEquals(u1.getUsername(), user.getUsername());
    }

    @Test
    @DisplayName("Test loadUserByUsername Invalid")
    void testLoadUserByUsernameMissing() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("invalidusername"));
    }

    @Test
    @DisplayName("Test loadUserByUsername Null")
    void testLoadUserByUsernameNull() {
        User u1 = new User(1L, "dondon94", "123Qwe!");
        doReturn(Optional.of(u1)).when(repo).findByUsername(u1.getUsername());
        assertNull(service.loadUserByUsername(null));
    }
}