package org.example.dao;

import org.example.entity.Item;
import org.hibernate.Session;

public interface ItemDAO extends CrudDAO<Item> {
    String getName(String itemId);

    double getPrice(String itemId);

    boolean updateQty(String itemId, int qty, Session session);
}
