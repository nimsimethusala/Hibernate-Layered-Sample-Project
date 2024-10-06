package org.example.dao;

import org.example.entity.Customer;

public interface CustomerDAO extends CrudDAO<Customer> {

    String customerName(String cusid);
}
