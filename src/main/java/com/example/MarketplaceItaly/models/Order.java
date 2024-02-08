package com.example.MarketplaceItaly.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    private Long id;
    @ManyToMany
    private Set<Product> products;
    private User user;
    private Long totalPrice;
    private Stages stage;

}
