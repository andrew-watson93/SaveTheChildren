/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Objects;

/**
 *
 * @author andre
 */
public class Customer {

    private String firstName;
    private String secondName;
    private Integer id;
    private String email;

    public Customer(String firstName, String secondName, Integer customerId, String email) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.id = customerId;
        this.email = email;
    }

    public Customer() {
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Customer) {
            Customer custObj = (Customer) obj;
            return Objects.equals(custObj.getFirstName(), this.firstName)
                    && Objects.equals(custObj.getSecondName(), this.secondName)
                    && Objects.equals(custObj.getId(), this.id)
                    && Objects.equals(custObj.getEmail(), this.email);
        }
        return false;
    }

}
