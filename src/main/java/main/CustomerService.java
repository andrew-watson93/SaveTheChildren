/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 *
 * @author andre
 */
@Service
@Repository
public class CustomerService {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final CustomerParameterMapper customerParameterMapper;

    private static final String INSERT_CUSTOMER
            = "INSERT INTO customer(firstname, secondname, email) VALUES(:firstname, :secondname, :email);";

    private static final String SELECT_CUSTOMER
            = "SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;";

    public CustomerService(NamedParameterJdbcTemplate jdbcTemplate, CustomerParameterMapper customerParameterMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerParameterMapper = customerParameterMapper;
    }

    public void encryptAndSave(Customer customer) throws Exception {
        jdbcTemplate.update(INSERT_CUSTOMER, customerParameterMapper.buildParamMap(customer));
    }

    public Customer findCustomer(Customer customer) throws Exception {
        return jdbcTemplate.queryForObject(
                SELECT_CUSTOMER,
                customerParameterMapper.buildParamMap(customer),
                new BeanPropertyRowMapper<>(Customer.class)
        );
    }

    public void updateCustomer(Customer eq) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
