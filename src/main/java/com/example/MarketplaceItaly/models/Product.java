package com.example.MarketplaceItaly.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name="null";
    private String description="null";
    private double price=0;
    private Boolean hasSale = false;
    private double backPrice = 0;
    @ElementCollection(targetClass = Category.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "product_categories", joinColumns = @JoinColumn(name = "product_id"))
    @Enumerated(EnumType.STRING)
    private Set<Category> category= new HashSet<>();;
    private Set<String> image= new HashSet<>();
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "product_header", joinColumns = @JoinColumn(name = "product_id"))
    private Category headCategory=null;
    private String URL_1="null";
    private String brand = "null";
    private String sku="null";
    private String name_en="null";
    private int weight=0;
    private int length=0;
    private int wide=0;
    private int height=0;
    private String size="null";
    private String color_ru="null";
    private String color_orig="null";

}
