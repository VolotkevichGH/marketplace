package com.example.MarketplaceItaly.repositories;

import com.example.MarketplaceItaly.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

}
