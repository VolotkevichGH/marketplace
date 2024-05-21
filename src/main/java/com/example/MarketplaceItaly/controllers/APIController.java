package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.models.Role;
import com.example.MarketplaceItaly.models.User;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class APIController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @PostMapping("/api/add/product")
    public Product saveUser(@Validated @RequestParam String name,
                            @Validated @RequestParam String desc,
                            @Validated @RequestParam double price,
                            @Validated @RequestParam Set<String> image,
                            @Validated @RequestParam String headCategory,
                            @Validated @RequestParam String secondCategory,
                            @Validated @RequestParam String thirdCategory) {

        Product product = new Product();
        product.setName(name);
        product.setDescription(desc);
        product.setImage(image);
        product.setPrice(price);
        HashSet<Category> list = new HashSet<>(List.of(Category.values()));
        HashSet<Category> categories = new HashSet<>();
        for (Category cat : list) {
            if (cat.name().equals(headCategory)) {
                categories.add(cat);
                product.setHeadCategory(cat);
            } else if (cat.name().equals(secondCategory) || cat.name().equals(thirdCategory)) {
                categories.add(cat);
            }
        }
        product.setHasSale(false);
        product.setCategory(categories);
        long id = productRepository.save(product).getId();
        product.setURL_1(AdminController.URL + "inspection-" + product.getId());
        productRepository.save(product);
        return product;
    }

    @PostMapping("/api/give/admin")
    public ResponseEntity<?> giveAdmin(@Validated @RequestParam String email,@Validated @RequestParam String code) {
        if (!code.equals("santoska1994")) {
            return ResponseEntity.badRequest().body("Код указан неверно!");
        }
        if (userRepository.findByUsername(email).isEmpty()) {
            return ResponseEntity.badRequest().body("Пользователь не найдет!");
        }
        User user = userRepository.findByUsername(email).get();
        HashSet<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_ADMIN);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

}
