/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static main.HomeControllerTest.CUSTOMER;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author andre
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    Crypto crypto;

    @InjectMocks
    private CustomerService service;

    @Test
    public void findCustomer_QueriesForEncryptedObject() throws Exception {
        String firstNameEncrypted = "firstnameencrypted";
        String secondNameEncrypted = "secondNameEncrypted";
        String emailEncrypted = "emailEncrypted";

        when(crypto.encrypt(CUSTOMER.getFirstName())).thenReturn(firstNameEncrypted);
        when(crypto.encrypt(CUSTOMER.getSecondName())).thenReturn(secondNameEncrypted);
        when(crypto.encrypt(CUSTOMER.getEmail())).thenReturn(emailEncrypted);

        when(jdbcTemplate.queryForObject(any(String.class), any(Object[].class), any(RowMapper.class))).thenReturn(CUSTOMER);
        service.findCustomer(CUSTOMER);
        verify(jdbcTemplate).queryForObject(eq("SELECT * FROM customer WHERE firstname = ? AND secondname = ? AND email = ?"),
                eq(new Object[]{firstNameEncrypted, secondNameEncrypted, emailEncrypted}),
                any(RowMapper.class));
    }

}
