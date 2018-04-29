/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;
import static main.HomeControllerTest.CUSTOMER;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 *
 * @author andre
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Mock
    private CustomerParameterMapper customerParameterMapper;

    @Mock
    private CustomerDecrypter customerDecrypter;

    @InjectMocks
    private CustomerService service;

    private static final Map<String, String> PARAM_MAP = new HashMap<>();

    @Before
    public void setup() throws Exception {
        PARAM_MAP.put("key", "value");
        when(customerParameterMapper.buildParamMapForInsertAndSelect(CUSTOMER)).thenReturn(PARAM_MAP);
        when(customerParameterMapper.buildParamMapForUpdate(CUSTOMER)).thenReturn(PARAM_MAP);
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;"),
                eq(PARAM_MAP),
                any(RowMapper.class)))
                .thenReturn(CUSTOMER);
    }

    @Test
    public void findCustomer_QueriesForEncryptedObject() throws Exception {
        service.findCustomer(CUSTOMER);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;"),
                eq(PARAM_MAP),
                any(RowMapper.class)
        );

    }

    @Test
    public void encryptAndSave() throws Exception {
        service.encryptAndSave(CUSTOMER);
        verify(jdbcTemplate).update(
                eq("INSERT INTO customer(firstname, secondname, email) VALUES(:firstname, :secondname, :email);"),
                eq(PARAM_MAP)
        );
    }

    @Test
    public void updateCustomer() throws Exception {
        service.updateCustomer(CUSTOMER);
        verify(jdbcTemplate).update(
                eq("UPDATE customer SET firstname = :firstname, secondname = :secondname, email = :email WHERE id = :id;"),
                eq(PARAM_MAP)
        );
    }

}
