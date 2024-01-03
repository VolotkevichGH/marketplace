package com.example.MarketplaceItaly.controllers;


import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.models.Role;
import com.example.MarketplaceItaly.models.User;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Set;


@Controller
@RequiredArgsConstructor
public class HomeController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    @GetMapping("/register")
    public String regPage(Model model){
        return "create";
    }

    @PostMapping("/register")
    public String regPost(Model model, @RequestParam String email, @RequestParam String password, @RequestParam String firstname, @RequestParam String lastname){

        boolean testUser = userRepository.findByUsername(email).isPresent();
        if (!testUser) {
            User user = new User();
            user.setName(firstname);
            user.setSurname(lastname);
            user.setPassword(passwordEncoder.encode(password));
            user.setUsername(email);
            user.setRoles(Set.of(Role.ROLE_ADMIN));
            userRepository.save(user);
            return "redirect:/";
        } else {
            return "redirect:/register";
        }
    }

    @GetMapping("/")
    public String homePage(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "D&G";
    }

    @PostMapping("/cartaddproduct-{product}")
    public String addProductToCart(Model model, @PathVariable Product product){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = (User) userRepository.findByUsername(name).get();
            ArrayList<Product> hashSet;
            try {
                if (user.getCart().isEmpty()) {
                    hashSet = new ArrayList<>();
                } else {
                    hashSet = new ArrayList<>(user.getCart());
                }
                hashSet.add(product);
                user.setCart(hashSet);
                userRepository.save(user);
            } catch (DataIntegrityViolationException e){
                System.out.println(e.getMessage());
            }
        }
        return "redirect:/";
    }




}
