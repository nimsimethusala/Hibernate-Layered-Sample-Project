module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires static lombok;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires com.jfoenix;


    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controller;
    opens org.example.entity;
    opens org.example.dto;
    opens org.example.dao;
    opens org.example.controller to javafx.fxml;

    opens org.example.tm to javafx.base;

    exports org.example.tm;
}