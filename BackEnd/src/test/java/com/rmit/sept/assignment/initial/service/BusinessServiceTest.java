package com.rmit.sept.assignment.initial.service;

import com.rmit.sept.assignment.initial.repositories.BusinessRepository;
import com.rmit.sept.assignment.initial.model.Business;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.any;

@SpringBootTest
@ActiveProfiles("test")
class BusinessServiceTest {
    @Autowired
    private BusinessService service;

    @MockBean
    private BusinessRepository repo;

    private final Long id = 1L;
    private final Business b1 = new Business(id, "New Business", "About the business");

    private List<Business> setupFindAll(Long businessId, String name, String substring) {
        List<Business> temp = new ArrayList<>();
        if (businessId != null) {
            Business b1 = new Business(businessId, name, "description");
            temp.add(b1);
            if (name != null) {
                if (substring == null) {
                    doReturn(temp).when(repo).findAllByNameIgnoreCase(name);
                }
                else {
                    doReturn(temp).when(repo).findAllByNameContainsIgnoreCase(substring);
                }
            }
        }
        doReturn(temp).when(repo).findAll();
        return temp;
    }

    @Test
    @DisplayName("Test findAll")
    void testFindAll() {
        setupFindAll(1L, "Business Name", null);
        Collection<Business> businesses;
        businesses = service.findAll();
        assertNotNull(businesses);
        assertEquals(1, businesses.size());
        setupFindAll(null, null, null);
        businesses = service.findAll();
        assertNotNull(businesses);
        assertEquals(0, businesses.size());
    }

    @Test
    @DisplayName("Test findByName")
    void testFindByName() {
        setupFindAll(1L, "Business Name", "name");
        Collection<Business> businesses;
        businesses = service.findByName("name", false);
        assertNotNull(businesses);
        assertEquals(1, businesses.size());
        businesses = service.findByName("KAren", false);
        assertNotNull(businesses);
        assertEquals(0, businesses.size());
    }

    @Test
    @DisplayName("Test findByNameExact")
    void testFindByNameExact() {
        setupFindAll(1L, "Business Name", null);
        Collection<Business> businesses;
        businesses = service.findByName("Business Name", true);
        assertNotNull(businesses);
        assertEquals(1, businesses.size());
        businesses = service.findByName("Business", true);
        assertNotNull(businesses);
        assertEquals(0, businesses.size());
    }

    @Test
    @DisplayName("Test findById Success")
    void testFindById() {
        doReturn(Optional.of(b1)).when(repo).findById(id);
        Business b2 = service.findById(id);
        assertNotNull(b2);
        assertEquals(b1, b2);
    }

    @Test
    @DisplayName("Test findById Not Found")
    void testFindByIdNotFound() {
        doReturn(Optional.of(b1)).when(repo).findById(id);
        Business b2 = service.findById(2L);
        assertNull(b2);
    }

    @Test
    @DisplayName("Test findById Null")
    void testFindByIdNull() {
        doReturn(Optional.of(b1)).when(repo).findById(id);
        Business b2 = service.findById(null);
        assertNull(b2);
    }

    @Test
    @DisplayName("Test saveOrUpdate Create Success")
    void testCreateBusiness() {
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(b1);
        assertNotNull(b2);
        assertEquals(b1, b2);
    }

    @Test
    @DisplayName("Test saveOrUpdate Null Id")
    void testCreateBusinessNullId() {
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(new Business(null,"Test", "test"));
        assertNotNull(b2);
        assertEquals(b1, b2);
    }

    @Test
    @DisplayName("Test saveOrUpdate Empty Request")
    void testCreateBusinessBadRequest() {
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(new Business());
        assertNull(b2);
    }

    @Test
    @DisplayName("Test saveOrUpdate Null Name")
    void testCreateBusinessNullName() {
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(new Business(1L,null, "test"));
        assertNull(b2);
    }


    @Test
    @DisplayName("Test saveOrUpdate Update Success")
    void testUpdateBusiness() {
        // create business
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(b1);
        assertNotNull(b2);
        assertEquals(b1, b2);
        // update name
        b2.setName("New Name");
        doReturn(b2).when(repo).save(any());
        // check if update success
        Business b3 = service.saveOrUpdateBusiness(b2);
        assertNotNull(b3);
        assertTrue(b2.getName().equalsIgnoreCase(b3.getName()));
    }

    @Test
    @DisplayName("Test saveOrUpdate Name Null")
    void testUpdateBusinessNameNull() {
        // create business
        doReturn(b1).when(repo).save(any());
        Business b2 = service.saveOrUpdateBusiness(b1);
        assertNotNull(b2);
        assertEquals(b1, b2);
        // update name to null
        b2.setName(null);
        doReturn(b2).when(repo).save(any());
        // check if handles null
        Business b3 = service.saveOrUpdateBusiness(b2);
        assertNull(b3);
    }
}