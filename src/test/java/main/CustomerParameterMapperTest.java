/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Map;
import static main.HomeControllerTest.CUSTOMER_WITH_ID;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
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

    @Before
    public void setup() throws Exception {
        when(crypto.encrypt(CUSTOMER_WITH_ID.getFirstName())).thenReturn(ENCRYPTED_FIRST_NAME);
        when(crypto.encrypt(CUSTOMER_WITH_ID.getSecondName())).thenReturn(ENCRYPTED_SECOND_NAME);
        when(crypto.encrypt(CUSTOMER_WITH_ID.getEmail())).thenReturn(ENCRYPTED_EMAIL);
    }

    @Test
    public void testBuildParamMapForInsertAndSelect() throws Exception {
        Map<String, String> params = customerParameterMapper.buildParamMapForInsertAndSelect(CUSTOMER_WITH_ID);
        assertThat(params.size(), is(3));
        testDefaultAssertions(params);

    }

    @Test
    public void testBuildParamMapForUpdate() throws Exception {
        Map<String, String> params = customerParameterMapper.buildParamMapForUpdate(CUSTOMER_WITH_ID);
        assertThat(params.size(), is(4));
        testDefaultAssertions(params);
        testIdAssertions(params);
    }

    @Test
    public void testBuildParameterForSelectById() {
        Map<String, String> params = customerParameterMapper.buildParameterForSelectById(CUSTOMER_WITH_ID);
        assertThat(params.size(), is(1));
        testIdAssertions(params);
    }

    private void testIdAssertions(Map<String, String> params) {
        assertTrue(params.keySet().contains("id"));
        assertThat(params.get("id"), is(CUSTOMER_WITH_ID.getId().toString()));
    }

    private void testDefaultAssertions(Map<String, String> params) {
        assertTrue(params.keySet().contains("firstname"));
        assertTrue(params.keySet().contains("secondname"));
        assertTrue(params.keySet().contains("email"));
        assertThat(params.get("firstname"), is(ENCRYPTED_FIRST_NAME));
        assertThat(params.get("secondname"), is(ENCRYPTED_SECOND_NAME));
        assertThat(params.get("email"), is(ENCRYPTED_EMAIL));
    }

}
