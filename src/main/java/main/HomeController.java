/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author andre
 */
@RestController
public class HomeController {

    private final CustomerService customerService;
    private final CustomerValidator customerValidator;

    public HomeController(CustomerService service, CustomerValidator customerValidator) {
        this.customerService = service;
        this.customerValidator = customerValidator;
    }

    @PostMapping("/save")
    public @ResponseBody
    ResponseEntity saveCustomer(@RequestBody Customer customer) throws Exception {
        customerValidator.validateAttributes(customer);
        Customer existing = customerService.findCustomer(customer);
        if (existing != null) {
            customerService.updateCustomer(customer);
        } else {
            customerService.encryptAndSave(customer);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}
