package org.example.dao;

import org.example.dao.impl.CustomerDaoImpl;
import org.example.dao.impl.ItemDaoImpl;
import org.example.dao.impl.OrderDaoImpl;
import org.example.dao.impl.OrderDetailDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDaoImpl();
            case ITEM:
                return new ItemDaoImpl();
            case ORDER:
                return new OrderDaoImpl();
            case ORDER_DETAIL:
                return new OrderDetailDaoImpl();
            default:
                return null;
        }
    }
}
