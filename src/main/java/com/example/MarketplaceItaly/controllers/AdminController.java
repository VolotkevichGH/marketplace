package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepository productRepository;

    @GetMapping("")
    public String adminPage(Model model) {
        HashSet<Product> products = new HashSet<>(productRepository.findAll());
        model.addAttribute("products", products);
        return "Bok";
    }

    @GetMapping("/orders")
    public String admin1Page(Model model) {
        HashSet<Product> products = new HashSet<>(productRepository.findAll());
        model.addAttribute("products", products);
        return "bok1";
    }

    @GetMapping("/buyers")
    public String admin2Page(Model model) {
        HashSet<Product> products = new HashSet<>(productRepository.findAll());
        model.addAttribute("products", products);
        return "bok2";
    }


    @GetMapping("/addproduct")
    public String addProduct(Model model) {
        HashSet<Category> list = new HashSet<>(List.of(Category.values()));
        model.addAttribute("categories", list);
        return "addproduct";
    }



    @PostMapping("/addproduct")
    public String postAddProduct(@RequestParam String name, @RequestParam String desc, @RequestParam double price,
                                 @RequestParam String image, @RequestParam String category) {
        HashSet<Category> hashSet = new HashSet<>();
        String[] massive = category.split(",");
        for (String str : massive) {
            str.trim();
            hashSet.add(Category.valueOf(str));
        }


        Product product = new Product();
        product.setName(name);
        if (hashSet.contains(Category.men)) {
            product.setHeadCategory(Category.men);
        } else if (hashSet.contains(Category.women)) {
            product.setHeadCategory(Category.women);
        } else if (hashSet.contains(Category.child)) {
            product.setHeadCategory(Category.child);
        }
        product.setPrice(price);
        product.setImage(image);
        product.setDescription(desc);
        product.setCategory(hashSet);
        product.setHasSale(false);
        productRepository.save(product);
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

    @PostMapping("/sale-{product}")
    public String addSalesByOne(@RequestParam double sale, @PathVariable Product product) {

        if (product.getHasSale()) {
            product.setPrice((100 - sale) / 100 * product.getBackPrice());
            productRepository.save(product);
        } else {
            product.setBackPrice(product.getPrice());
            product.setPrice((100 - sale) / 100 * product.getPrice());
            product.setHasSale(true);
            productRepository.save(product);
        }

        return "redirect:/admin";
    }

    @PostMapping("/delsale-{product}")
    public String delSalesByProduct(@PathVariable Product product) {
        if (product.getHasSale()) {
            product.setPrice(product.getBackPrice());
            product.setHasSale(false);
            productRepository.save(product);
        }

        return "redirect:/admin";
    }

}
