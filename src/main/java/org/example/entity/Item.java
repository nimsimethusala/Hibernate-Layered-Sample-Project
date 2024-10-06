package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Item {
    @Id
    private String itemId;
    private String itemName;
    private int count;
    private double price;

    @ManyToMany
    private List<Orders> ordersList;

    public Item (String itemId, String itemName, int count, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.count = count;
        this.price = price;
    }
}
