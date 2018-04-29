/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.junit.Test;

/**
 *
 * @author andre
 */
public class CustomerValidatorTest {

    private CustomerValidator customerValidator = new CustomerValidator();

    @Test(expected = IllegalArgumentException.class)
    public void validateAttributes_ThrowsException_IfNoFirstNameProvided() {
        Customer noFirstName = new Customer(null, "second", 1, "email");
        customerValidator.validateAttributes(noFirstName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateAttributes_ThrowsException_IfNoSecondNameProvided() {
        Customer noFirstName = new Customer("first", null, 1, "email");
        customerValidator.validateAttributes(noFirstName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void validateAttributes_ThrowsException_IfNoEmailProvided() {
        Customer noEmail = new Customer("first", "second", 1, null);
        customerValidator.validateAttributes(noEmail);
    }

}
