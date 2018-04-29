/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;
import static main.HomeControllerTest.CUSTOMER_WITH_ID;
import static main.HomeControllerTest.CUSTOMER_WITH_NO_ID;
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
    private CustomerValidator customerValidator;

    @InjectMocks
    private CustomerService service;

    private static final Map<String, String> PARAM_MAP = new HashMap<>();

    @Before
    public void setup() throws Exception {
        PARAM_MAP.put("key", "value");
        when(customerParameterMapper.buildParamMapForInsertAndSelect(CUSTOMER_WITH_NO_ID)).thenReturn(PARAM_MAP);
        when(customerParameterMapper.buildParamMapForUpdate(CUSTOMER_WITH_ID)).thenReturn(PARAM_MAP);
        when(customerParameterMapper.buildParameterForSelectById(CUSTOMER_WITH_ID)).thenReturn(PARAM_MAP);
        when(jdbcTemplate.queryForObject(
                eq("SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;"),
                eq(PARAM_MAP),
                any(RowMapper.class)))
                .thenReturn(CUSTOMER_WITH_ID);
    }

    @Test
    public void findCustomer_QueriesForEncryptedObject() throws Exception {
        service.findCustomer(CUSTOMER_WITH_NO_ID);
        verify(customerValidator).validateAttributes(eq(CUSTOMER_WITH_NO_ID));
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;"),
                eq(PARAM_MAP),
                any(RowMapper.class)
        );
    }

    @Test
    public void findCustomer_TriesToFindByIdIfProvided() throws Exception {
        service.findCustomer(CUSTOMER_WITH_ID);
        verify(jdbcTemplate).queryForObject(
                eq("SELECT * FROM customer WHERE id = :id;"),
                eq(PARAM_MAP),
                any(RowMapper.class)
        );
    }

    @Test
    public void encryptAndSave() throws Exception {
        service.encryptAndSave(CUSTOMER_WITH_NO_ID);
        verify(customerValidator).validateAttributes(eq(CUSTOMER_WITH_NO_ID));
        verify(jdbcTemplate).update(
                eq("INSERT INTO customer(firstname, secondname, email) VALUES(:firstname, :secondname, :email);"),
                eq(PARAM_MAP)
        );
    }

    @Test
    public void updateCustomer() throws Exception {
        service.updateCustomer(CUSTOMER_WITH_ID);
        verify(customerValidator).validateAttributes(eq(CUSTOMER_WITH_ID));
        verify(jdbcTemplate).update(
                eq("UPDATE customer SET firstname = :firstname, secondname = :secondname, email = :email WHERE id = :id;"),
                eq(PARAM_MAP)
        );
    }

}
