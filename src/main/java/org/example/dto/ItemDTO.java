package org.example.dto;

import jakarta.persistence.Entity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ItemDTO {
    private String itemId;
    private String itemName;
    private int count;
    private double price;
}
