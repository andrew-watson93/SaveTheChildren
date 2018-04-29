/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import org.springframework.dao.EmptyResultDataAccessException;
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

    private static final String SELECT_CUSTOMER_BY_ATTRIBUTES
            = "SELECT * FROM customer WHERE firstname = :firstname AND secondname = :secondname AND email = :email;";

    private static final String SELECT_CUSTOMER_BY_ID
            = "SELECT * FROM customer WHERE id = :id;";

    private static final String UPDATE_CUSTOMER
            = "UPDATE customer SET firstname = :firstname, secondname = :secondname, email = :email WHERE id = :id;";

    public CustomerService(
            NamedParameterJdbcTemplate jdbcTemplate,
            CustomerParameterMapper customerParameterMapper
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerParameterMapper = customerParameterMapper;
    }

    public void encryptAndSave(Customer customer) throws Exception {
        jdbcTemplate.update(INSERT_CUSTOMER, customerParameterMapper.buildParamMapForInsertAndSelect(customer));
    }

    public Customer findCustomer(Customer customer) throws Exception {
        try {
            return customer.getId() != null ? findById(customer) : findByAttributes(customer);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void updateCustomer(Customer customer) throws Exception {
        jdbcTemplate.update(
                UPDATE_CUSTOMER,
                customerParameterMapper.buildParamMapForUpdate(customer)
        );

    }

    private Customer findById(Customer customer) {
        return jdbcTemplate.queryForObject(
                SELECT_CUSTOMER_BY_ID,
                customerParameterMapper.buildParameterForSelectById(customer),
                new BeanPropertyRowMapper<>(Customer.class
                )
        );
    }

    private Customer findByAttributes(Customer customer) throws Exception {
        return jdbcTemplate.queryForObject(
                SELECT_CUSTOMER_BY_ATTRIBUTES,
                customerParameterMapper.buildParamMapForInsertAndSelect(customer),
                new BeanPropertyRowMapper<>(Customer.class
                )
        );
    }

}
