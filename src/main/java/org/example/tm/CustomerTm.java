package org.example.tm;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class CustomerTm {
    @Id
    private String customerId;
    private String name;
    private String address;
    private int contact;
}
