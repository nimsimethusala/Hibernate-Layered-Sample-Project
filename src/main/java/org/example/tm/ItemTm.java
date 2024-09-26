package org.example.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemTm {
    private String itemId;
    private String itemName;
    private int count;
    private double price;
}
