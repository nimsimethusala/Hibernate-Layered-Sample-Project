package org.example.dao.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.OrderDAO;
import org.example.entity.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderDaoImpl implements OrderDAO {
    @Override
    public String generateNextOrderId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1");
        String orderId = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return splitOrderId(orderId);
    }

    private String splitOrderId(String string) {
        if(string != null) {
            String[] strings = string.split("O0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "O00"+id;
            }else {
                if (length < 3){
                    return "O0"+id;
                }else {
                    return "O"+id;
                }
            }
        }
        return "O001";
    }

    @Override
    public boolean save(Orders orders, Session session) {
        session.save(orders);
        return true;
    }
}
