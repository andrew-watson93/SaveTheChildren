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

    private final Crypto crypto;

    public CustomerParameterMapper(Crypto crypto) {
        this.crypto = crypto;
    }

    public Map<String, String> buildParamMapForInsertAndSelect(Customer customer) throws Exception {
        return createDefaultParamMap(customer);
    }

    public Map<String, String> buildParamMapForUpdate(Customer customer) throws Exception {
        Map<String, String> map = createDefaultParamMap(customer);
        map.put("id", customer.getId().toString());
        return map;
    }

    public Map<String, String> buildParameterForSelectById(Customer customer) {
        Map<String, String> map = new HashMap<>();
        map.put("id", customer.getId().toString());
        return map;
    }

    private Map<String, String> createDefaultParamMap(Customer customer) throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("firstname", crypto.encrypt(customer.getFirstName()));
        parameters.put("secondname", crypto.encrypt(customer.getSecondName()));
        parameters.put("email", crypto.encrypt(customer.getEmail()));
        return parameters;
    }

}
