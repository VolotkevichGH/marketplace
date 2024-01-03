package com.example.MarketplaceItaly.repositories;

import com.example.MarketplaceItaly.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
