package org.example.dao;

import org.example.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO{
    boolean save(T customer);

    boolean update(T customer);

    String generateNextId();

    public boolean delete(String cusId) throws SQLException, ClassNotFoundException;

    public T searchById(String cusid) throws SQLException, ClassNotFoundException;

    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;
}
