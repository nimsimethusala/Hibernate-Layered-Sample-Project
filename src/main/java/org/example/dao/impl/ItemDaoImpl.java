package org.example.dao.impl;

import org.example.config.FactoryConfiguration;
import org.example.dao.ItemDAO;
import org.example.entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements ItemDAO {
    @Override
    public boolean save(Item customer) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(customer);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Item item) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(item);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String itemId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE FROM Item WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);
        query.executeUpdate();

        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Item searchById(String itemId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Item WHERE itemId = :itemId");
        query.setParameter("itemId", itemId);

        List<Item> items = query.list();

        for (Item item : items){
            String itemid = item.getItemId();
            String name = item.getItemName();
            int count = item.getCount();
            double price = item.getPrice();

            Item item1 = new Item(itemid, name, count, price);
            transaction.commit();
            session.close();

            return item1;
        }
        return null;
    }

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("FROM Item");
        ArrayList<Item> items = (ArrayList<Item>) query.list();

        transaction.commit();
        session.close();
        return items;
    }

    @Override
    public String generateNextId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT itemId FROM Item ORDER BY itemId DESC LIMIT 1");
        String itemid = (String) query.uniqueResult();
        transaction.commit();
        session.close();
        return splitItemId(itemid);
    }

    private String splitItemId(String string) {
        if(string != null) {
            String[] strings = string.split("I0");
            int id = Integer.parseInt(strings[1]);
            id++;
            String ID = String.valueOf(id);
            int length = ID.length();
            if (length < 2){
                return "I00"+id;
            }else {
                if (length < 3){
                    return "I0"+id;
                }else {
                    return "I"+id;
                }
            }
        }
        return "I001";
    }

    @Override
    public String getName(String itemId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT itemName from Item where itemId = :itemId");
        query.setParameter("itemId", itemId);
        String itemName = (String) query.uniqueResult();

        transaction.commit();
        session.close();

        return itemName;
    }

    @Override
    public double getPrice(String itemId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT price from Item where itemId = :itemId");
        query.setParameter("itemId", itemId);
        double price = (double) query.uniqueResult();

        transaction.commit();
        session.close();

        return price;
    }

    @Override
    public boolean updateQty(String itemId, int qty, Session session) {
        Query query = session.createQuery("update Item set count = count - ?1 where itemId = ?2");
        query.setParameter(1, qty);
        query.setParameter(2, itemId);

        int result = query.executeUpdate();

        if (result > 0) {
            return true;
        }

        return false;
    }
}
