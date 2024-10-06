package org.example.dao;

import org.example.entity.Orders;
import org.hibernate.Session;

public interface OrderDAO extends SuperDAO{
    String generateNextOrderId();

    boolean save(Orders orders, Session session);
}
