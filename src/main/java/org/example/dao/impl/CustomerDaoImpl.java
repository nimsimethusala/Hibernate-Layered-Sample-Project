package org.example.dao.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.CustomerDAO;
import org.example.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDAO {
    @Override
    public String customerName(String cusid) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT name from Customer where customerId = :cusid");
        query.setParameter("cusid", cusid);
        String cusName = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return cusName;
    }

    @Override
    public boolean save(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Customer customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public String generateNextId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT customerId FROM Customer ORDER BY customerId DESC LIMIT 1");
        String customerId = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return splitCustomerId(customerId);
    }

    @Override
    public boolean delete(String cusId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE FROM Customer WHERE customerId = :cusId");
        query.setParameter("cusId", cusId);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Customer searchById(String cusid) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Customer WHERE customerId = :cusid");
        query.setParameter("cusid", cusid);

        List<Customer> customers = query.list();

        for (Customer customer : customers){
            String customerId = customer.getCustomerId();
            String name = customer.getName();
            String address = customer.getAddress();
            int contact = customer.getContact();

            Customer customer1 = new Customer(customerId, name, address, contact);
            transaction.commit();
            session.close();
            return customer1;
        }
        return null;
    }

    @Override
    public ArrayList<Customer> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Customer");
        ArrayList<Customer> customers = (ArrayList<Customer>) query.list();

        transaction.commit();
        session.close();
        return customers;
    }

    private String splitCustomerId(String string) {
        if(string != null) {
            String[] strings = string.split("C0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "C00"+id;
            }else {
                if (length < 3){
                    return "C0"+id;
                }else {
                    return "C"+id;
                }
            }
        }
        return "C001";
    }
}
