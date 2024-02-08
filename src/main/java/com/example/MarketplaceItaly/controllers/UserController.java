package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.models.User;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/add-product-{product}")
    public String addProduct(@PathVariable Product product) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            if (user.getCart().isEmpty()) {
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                user.setCart(products);
                userRepository.save(user);
            } else {
                ArrayList<Product> products = new ArrayList<>(user.getCart());
                products.add(product);
                user.setCart(products);
                userRepository.save(user);
            }
            return "redirect:/inspection-" + product.getId() + "?";
        }
        return "redirect:/register";
    }

    @PostMapping("/changedata")
    public String changeData(@RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam String password){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = (User) userRepository.findByUsername(username).get();
        if (!name.isEmpty()) {
            user.setName(name);
        }
        if (!surname.isEmpty()) {
            user.setSurname(surname);
        }
        if (!email.isEmpty()) {
            user.setUsername(email);
        }
        if (!password.isEmpty()) {
            user.setPassword(passwordEncoder.encode(password));
        }
        userRepository.save(user);
        return "redirect:/";
    }





}
