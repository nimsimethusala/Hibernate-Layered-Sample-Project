package org.example.dao.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.OrderDetailDAO;
import org.example.entity.OrderDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDaoImpl implements OrderDetailDAO {

    @Override
    public boolean save(OrderDetail customer) {
        return false;
    }

    @Override
    public boolean update(OrderDetail customer) {
        return false;
    }

    @Override
    public String generateNextId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT orderDetailId FROM OrderDetail ORDER BY orderDetailId DESC LIMIT 1");
        String orderDetailid = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return splitOrderDetailId(orderDetailid);
    }

    private String splitOrderDetailId(String string) {
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
    public boolean delete(String cusId) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public OrderDetail searchById(String cusid) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(List<OrderDetail> orderDetailList, Session session) {
        for (OrderDetail orderDetail : orderDetailList){
            save(orderDetail, session);
            return true;
        }
        return false;
    }

    public boolean save(OrderDetail orderDetail, Session session){
        session.save(orderDetail);
        return true;
    }
}
