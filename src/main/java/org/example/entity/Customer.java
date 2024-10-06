package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Customer {
    @Id
    private String customerId;
    private String name;
    private String address;
    private int contact;

    @OneToMany(mappedBy = "customer")
    private List<Orders> ordersList;

    public Customer (String customerId, String name, String address, int contact) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }
}
