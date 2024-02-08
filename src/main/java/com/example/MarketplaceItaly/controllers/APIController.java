package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class APIController {

    private final ProductRepository productRepository;
        @PostMapping("/api/add/product")
    public Product saveUser(@Validated @RequestParam String name, @Validated @RequestParam String desc, @Validated @RequestParam double price,
                            @Validated @RequestParam String image, @Validated @RequestParam String headCategory,
                            @Validated @RequestParam String secondCategory, @Validated @RequestParam String thirdCategory) {

            Product product = new Product();
            product.setName(name);
            product.setDescription(desc);
            product.setImage(image);
            product.setPrice(price);
            HashSet<Category> list = new HashSet<>(List.of(Category.values()));
            HashSet<Category> categories = new HashSet<>();
            for (Category cat : list){
                if (cat.name().equals(headCategory)){
                    categories.add(cat);
                    product.setHeadCategory(cat);
                } else if (cat.name().equals(secondCategory) || cat.name().equals(thirdCategory)){
                    categories.add(cat);
                }
            }
            product.setHasSale(false);
            product.setCategory(categories);
            productRepository.save(product);
        return product;
    }

}
