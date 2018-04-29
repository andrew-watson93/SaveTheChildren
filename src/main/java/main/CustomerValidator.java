/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
class CustomerValidator {

    public void validateAttributes(Customer customer) {
        if (StringUtils.isBlank(customer.getFirstName())) {
            throw new IllegalArgumentException("First name cannot be blank");
        } else if (StringUtils.isBlank(customer.getSecondName())) {
            throw new IllegalArgumentException("Second name cannot be blank");
        } else if (StringUtils.isBlank(customer.getEmail())) {
            throw new IllegalArgumentException("Email cannot be blank");
        }

    }

}
