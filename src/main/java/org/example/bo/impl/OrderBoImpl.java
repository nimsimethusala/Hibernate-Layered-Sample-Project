package org.example.bo.impl;

import org.example.bo.OrderBO;
import org.example.dao.DAOFactory;
import org.example.dao.OrderDAO;

public class OrderBoImpl implements OrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);

    @Override
    public String generateNextOrderId() {
        return orderDAO.generateNextOrderId();
    }
}
