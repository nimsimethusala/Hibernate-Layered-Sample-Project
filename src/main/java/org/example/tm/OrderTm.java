package org.example.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class OrderTm {
    private String cusId;
    private String itemId;
    private double price;
    private int qty;
    private double total;
}
