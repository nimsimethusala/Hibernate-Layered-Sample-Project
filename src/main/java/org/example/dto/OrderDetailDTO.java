package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.example.entity.Item;
import org.example.entity.Orders;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private String orderDetailId;
    private Orders orders;
    private Item item;
    private int qty;
}
