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
    private final Crypto crypto;

    private static final String INSERT_CUSTOMER
            = "INSERT INTO customer(firstname, secondname, email) VALUES(:firstname, :secondname, :email);";

    private static final String SELECT_CUSTOMER
            = "SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;";

    private static final String UPDATE_CUSTOMER
            = "UPDATE customer SET firstname = :firstname, secondname = :secondname, email = :email WHERE id = :id;";

    public CustomerService(NamedParameterJdbcTemplate jdbcTemplate, CustomerParameterMapper customerParameterMapper, Crypto crypto) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerParameterMapper = customerParameterMapper;
        this.crypto = crypto;
    }

    public void encryptAndSave(Customer customer) throws Exception {
        jdbcTemplate.update(INSERT_CUSTOMER, customerParameterMapper.buildParamMapForInsertAndSelect(customer, crypto));
    }

    public Customer findCustomer(Customer customer) throws Exception {
        return jdbcTemplate.queryForObject(
                SELECT_CUSTOMER,
                customerParameterMapper.buildParamMapForInsertAndSelect(customer, crypto),
                new BeanPropertyRowMapper<>(Customer.class)
        );
    }

    public void updateCustomer(Customer customer) throws Exception {
        jdbcTemplate.update(
                UPDATE_CUSTOMER,
                customerParameterMapper.buildParamMapForUpdate(customer, crypto)
        );
    }

}
