package org.example.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.example.entity.Customer;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private String orderId;
    private double netTotal;
    private Customer customer;
}
