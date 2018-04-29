/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Map;
import static main.HomeControllerTest.CUSTOMER;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author andre
 */
@RunWith(MockitoJUnitRunner.class)
public class CustomerParameterMapperTest {

    @Mock
    private Crypto crypto;

    @InjectMocks
    private CustomerParameterMapper customerParameterMapper;

    private static final String ENCRYPTED_FIRST_NAME = "ENCRYPTED_FIRST_NAME";
    private static final String ENCRYPTED_SECOND_NAME = "ENCRYPTED_SECOND_NAME";
    private static final String ENCRYPTED_EMAIL = "ENCRYPTED_EMAIL";

    @Test
    public void testBuildParamMap() throws Exception {
        when(crypto.encrypt(CUSTOMER.getFirstName())).thenReturn(ENCRYPTED_FIRST_NAME);
        when(crypto.encrypt(CUSTOMER.getSecondName())).thenReturn(ENCRYPTED_SECOND_NAME);
        when(crypto.encrypt(CUSTOMER.getEmail())).thenReturn(ENCRYPTED_EMAIL);
        Map<String, String> params = customerParameterMapper.buildParamMap(CUSTOMER, crypto);
        assertThat(params.size(), is(3));
        assertTrue(params.keySet().contains("firstname"));
        assertTrue(params.keySet().contains("secondname"));
        assertTrue(params.keySet().contains("email"));
        assertThat(params.get("firstname"), is(ENCRYPTED_FIRST_NAME));
        assertThat(params.get("secondname"), is(ENCRYPTED_SECOND_NAME));
        assertThat(params.get("email"), is(ENCRYPTED_EMAIL));

    }

}
