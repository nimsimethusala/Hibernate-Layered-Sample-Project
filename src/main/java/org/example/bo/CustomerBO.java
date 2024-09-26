package org.example.bo;

import org.example.dto.CustomerDTO;

import java.sql.SQLException;
import java.util.List;

public interface CustomerBO extends SuperBO{
    boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException;

    boolean updateCustomer(CustomerDTO customerDTO);

    String generateNextCustomerId();

    public boolean deleteCustomer(String cusId) throws SQLException, ClassNotFoundException;

    public CustomerDTO searchByCustomerId(String cusid) throws SQLException, ClassNotFoundException;

    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;
}
