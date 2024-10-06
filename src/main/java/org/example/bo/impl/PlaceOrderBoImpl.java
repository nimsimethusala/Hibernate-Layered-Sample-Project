package org.example.bo.impl;

import javafx.scene.control.Alert;
import org.example.bo.PlaceOrderBO;
import org.example.config.FactoryConfiguration;
import org.example.dao.DAOFactory;
import org.example.dao.ItemDAO;
import org.example.dao.OrderDAO;
import org.example.dao.OrderDetailDAO;
import org.example.dto.OrderDTO;
import org.example.entity.Item;
import org.example.entity.OrderDetail;
import org.example.entity.Orders;
import org.example.tm.OrderTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBoImpl implements PlaceOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAIL);

    @Override
    public boolean placeOrder(List<OrderTm> orderTmList, OrderDTO orderDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        boolean isUpdated = false;

        try {
            Orders orders = new Orders(orderDTO.getOrderId(), orderDTO.getNetTotal(), orderDTO.getCustomer());
            boolean isSaved = orderDAO.save(orders, session);
            if (isSaved) {
                List<OrderDetail> orderDetails = new ArrayList<>();
                for (OrderTm orderTm : orderTmList) {
                    Item item = new Item();
                    item.setItemId(orderTm.getItemId());
                    String itemId = orderTm.getItemId();
                    int qty = orderTm.getQty();
                    isUpdated = itemDAO.updateQty(itemId, qty, session);

                    String orderDetailId = orderDetailDAO.generateNextId();

                    orderDetails.add(new OrderDetail(orderDetailId, orders, item, qty));
                }

                if (isUpdated) {
                    orderDetailDAO.save(orderDetails, session);
                    transaction.commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Transaction Completed...!").show();
                    return true;
                }

            }
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            transaction.rollback();
        }finally {
            session.close();
        }
        return false;
    }
}
