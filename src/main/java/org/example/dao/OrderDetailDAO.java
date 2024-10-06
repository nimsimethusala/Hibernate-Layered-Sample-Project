package org.example.dao;

import org.example.entity.OrderDetail;
import org.hibernate.Session;

import java.util.List;

public interface OrderDetailDAO extends CrudDAO<OrderDetail> {
    boolean save(List<OrderDetail> orderDetailList, Session session);
}
