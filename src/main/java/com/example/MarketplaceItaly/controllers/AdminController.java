package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.repositories.CategoryRepository;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @GetMapping("")
    public String adminPage(Model model) {
        HashSet<Product> products = new HashSet<>(productRepository.findAll());
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/addproduct")
    public String addProduct(Model model) {
        HashSet categories = new HashSet(categoryRepository.findAll());
        model.addAttribute("categories", categories);
        return "addproduct";
    }

    @PostMapping("/addproduct")
    public String postAddProduct(@RequestParam String name, @RequestParam String desc, @RequestParam double price, @RequestParam String image, @RequestParam HashSet<String> categories) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setImage(image);
        product.setDescription(desc);
        HashSet<Category> hashSet = new HashSet<>();
        for (String nameCategory : categories) {
            Category category = categoryRepository.findByName(nameCategory);
            hashSet.add(category);
        }
        product.setHasSale(false);
        product.setCategory(hashSet);
        productRepository.save(product);
        return "redirect:/admin";
    }

    @PostMapping("/addcategory")
    public String addCategory(@RequestParam String catName) {
        Category category = new Category();
        category.setName(catName);
        categoryRepository.save(category);
        return "redirect:/admin";
    }

    @PostMapping("/allsales")
    public String addSales(@RequestParam double sale) {

        for (Product product : productRepository.findAll()) {
            if (product.getHasSale()) {
                product.setPrice((100 - sale) / 100 * product.getBackPrice());
                productRepository.save(product);
            } else {
                product.setBackPrice(product.getPrice());
                product.setPrice((100 - sale) / 100 * product.getPrice());
                product.setHasSale(true);
                productRepository.save(product);
            }
        }
        return "redirect:/admin";
    }

    @PostMapping("/delsales")
    public String delSales() {
        for (Product product : productRepository.findAll()) {
            if (product.getHasSale()) {
                product.setPrice(product.getBackPrice());
                product.setHasSale(false);
                productRepository.save(product);
            }
        }
        return "redirect:/admin";
    }

}
