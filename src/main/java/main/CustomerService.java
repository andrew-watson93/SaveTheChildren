/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
@Repository
public class CustomerService {

    private final JdbcTemplate jdbcTemplate;
    private final Crypto crypto;

    public CustomerService(JdbcTemplate jdbcTemplate, Crypto crypto) {
        this.jdbcTemplate = jdbcTemplate;
        this.crypto = crypto;
    }

    public void encryptAndSave(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Customer findCustomer(Customer customer) throws Exception {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM customer WHERE firstname = ? AND secondname = ? AND email = ?",
                new Object[]{
                    crypto.encrypt(customer.getFirstName()),
                    crypto.encrypt(customer.getSecondName()),
                    crypto.encrypt(customer.getEmail())
                },
                new BeanPropertyRowMapper<>(Customer.class)
        );

    }

    public void updateCustomer(Customer eq) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
