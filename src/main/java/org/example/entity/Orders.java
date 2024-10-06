package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Orders {
    @Id
    private String orderId;
    private double netTotal;

    @ManyToOne
    private Customer customer;

    @ManyToMany(mappedBy = "ordersList")
    private List<Item> itemList;

    public Orders(String orderId, double totalAmount, Customer customer) {
        this.orderId = orderId;
        netTotal = totalAmount;
        this.customer = customer;
    }
}
