package org.example.config;

import org.example.dto.CustomerDTO;
import org.example.entity.Customer;
import org.example.entity.Item;
import org.example.entity.OrderDetail;
import org.example.entity.Orders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class FactoryConfiguration {
    private static FactoryConfiguration factoryConfiguration;

    private SessionFactory sessionFactory;

    private FactoryConfiguration() {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Customer.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Orders.class);
        configuration.addAnnotatedClass(OrderDetail.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;
    }

    public Session getSession() {
        return sessionFactory.openSession();
    }
}
