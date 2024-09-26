package org.example.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomerDTO {
    private String customerId;
    private String name;
    private String address;
    private int contact;
}
