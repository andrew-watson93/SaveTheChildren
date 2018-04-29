/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author andre
 */
@Controller
public class HomeController {

    private final CustomerService service;

    public HomeController(CustomerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public @ResponseBody
    ResponseEntity saveCustomer(@RequestBody Customer customer) {
        Customer existing = service.findCustomer(customer);
        if (existing != null) {
            service.updateCustomer(customer);
        } else {
            service.encryptAndSave(customer);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

}