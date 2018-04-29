/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
class CustomerParameterMapper {

    public Map<String, String> buildParamMap(Customer customer, Crypto crypto) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("firstname", crypto.encrypt(customer.getFirstName()));
        parameters.put("secondname", crypto.encrypt(customer.getSecondName()));
        parameters.put("email", crypto.encrypt(customer.getEmail()));
        return parameters;
    }

}
