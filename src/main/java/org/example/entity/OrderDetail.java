package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class OrderDetail {
    @Id
    private String orderDetailId;

    @ManyToOne
    private Orders orders;

    @ManyToOne
    private Item item;

    private int qty;
}
