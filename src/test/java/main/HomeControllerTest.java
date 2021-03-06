package main;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(MockitoJUnitRunner.class)
public class HomeControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Mock
    private CustomerService service;

    @Mock
    private CustomerValidator customerValidator;

    @InjectMocks
    private HomeController homeController;

    private static final String FIRST_NAME = "FIRST_NAME";
    private static final String SECOND_NAME = "SECOND_NAME";
    private static final Integer ID = 1;
    private static final String EMAIL = "EMAIL";

    public static final Customer CUSTOMER_WITH_ID = new Customer(FIRST_NAME, SECOND_NAME, ID, EMAIL);
    public static final Customer CUSTOMER_WITH_NO_ID = new Customer(FIRST_NAME, SECOND_NAME, null, EMAIL);

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();

    }

    @Test
    public void saveCustomer_EndpointExists() throws Exception {
        callSaveEndpoint();
    }

    @Test
    public void saveCustomer_CallsGetCustomer() throws Exception {
        callSaveEndpoint();
        verify(service).findCustomer(eq(CUSTOMER_WITH_ID));

    }

    @Test
    public void saveCustomer_CallsUpdateCustomerIfAlreadyExists() throws Exception {
        when(service.findCustomer(CUSTOMER_WITH_ID)).thenReturn(new Customer());
        callSaveEndpoint();
        verify(service).updateCustomer(eq(CUSTOMER_WITH_ID));
    }

    @Test
    public void saveCustomer_CallsCreateIfOneDoesNotExist() throws Exception {
        callSaveEndpoint();
        verify(service).encryptAndSave(eq(CUSTOMER_WITH_ID));
    }

    @Test
    public void saveCustomer_CallsValidator() throws Exception {
        callSaveEndpoint();
        verify(customerValidator).validateAttributes(eq(CUSTOMER_WITH_ID));
    }

    private void callSaveEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/save")
                .content(mapper.writeValueAsString(CUSTOMER_WITH_ID))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

}
