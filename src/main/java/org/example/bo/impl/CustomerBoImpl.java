package org.example.bo.impl;

import org.example.bo.CustomerBO;
import org.example.config.FactoryConfiguration;
import org.example.dao.CustomerDAO;
import org.example.dao.DAOFactory;
import org.example.dto.CustomerDTO;
import org.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerBoImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customerDTO) {
        return customerDAO.update(new Customer(customerDTO.getCustomerId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getContact()));
    }

    @Override
    public String generateNextCustomerId() {
        return customerDAO.generateNextId();
    }

    @Override
    public boolean deleteCustomer(String cusId) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(cusId);
    }

    @Override
    public CustomerDTO searchByCustomerId(String cusid) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.searchById(cusid);
        return new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getContact());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getAll();

        for (Customer customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO(customer.getCustomerId(), customer.getName(), customer.getAddress(), customer.getContact());
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    @Override
    public String customerName(String cusid) {
        return customerDAO.customerName(cusid);
    }

    @Override
    public List<String> getCustomerId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT customerId FROM Customer");
        List<String> customerIds = query.list();

        transaction.commit();
        session.close();
        return customerIds;
    }
}
