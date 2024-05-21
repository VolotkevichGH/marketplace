package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.services.Excel;
import com.example.MarketplaceItaly.services.telegram.TelegramBot;
import com.example.MarketplaceItaly.services.telegram.TelegramConfig;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepository productRepository;
    private final TelegramBot bot;
    private final TelegramConfig telegramConfig;
    public static String URL = "http://localhost:8080/";

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
    public String postAddProduct(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam String description,
            @RequestParam String image,
            @RequestParam double price,
            @RequestParam String BRAND,
            @RequestParam String SKU,
            @RequestParam String NAME_EN,
            @RequestParam int WEIGHT,
            @RequestParam int LENGTH,
            @RequestParam int WIDE,
            @RequestParam int HEIGHT,
            @RequestParam String SIZE,
            @RequestParam String COLOR_ORIG,
            @RequestParam String COLOR_RU,
            @RequestParam double PRICE_OLD) {
        HashSet<Category> hashSet = new HashSet<>();
        System.out.println(category);
        String[] massive = category.split(",");
        for (String str : massive) {
            str.trim();
            hashSet.add(Category.valueOf(str));
        }


        Product product = new Product();

        product.setBrand(BRAND);
        product.setSku(SKU);
        product.setName_en(NAME_EN);
        product.setWeight(WEIGHT);
        product.setLength(LENGTH);
        product.setWide(WIDE);
        product.setHeight(HEIGHT);
        product.setSize(SIZE);
        product.setColor_orig(COLOR_ORIG);
        product.setColor_ru(COLOR_RU);
        product.setBackPrice(PRICE_OLD);


        if (hashSet.contains(Category.men)) {
            product.setHeadCategory(Category.men);
        } else if (hashSet.contains(Category.women)) {
            product.setHeadCategory(Category.women);
        } else if (hashSet.contains(Category.child)) {
            product.setHeadCategory(Category.child);
        }

        HashSet<String> images = new HashSet<>();
        String[] massive1 = image.split(",");
        for (String str : massive1) {
            str.trim();
            images.add(str);
        }
        product.setPrice(price);
        product.setImage(images);
        product.setDescription(description);
        product.setCategory(hashSet);
        product.setHasSale(false);
        productRepository.save(product);
        product.setURL_1(URL+"inspection-"+product.getId());
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



    @SneakyThrows
    @PostMapping("/excel")
    public String enterToExcel() {
        Excel excel = new Excel();
        String fileName = excel.createTableALL(productRepository.findAll());

        return "redirect:/admin";
    }

    @GetMapping("/product/edit/{product}")
    public String editProduct(@PathVariable Product product, Model model) {
        model.addAttribute("product", product);
        return "adminedit";
    }

    @PostMapping("/product/edit/{product}")
    public String editProductPost(@PathVariable Product product,
                                  @RequestParam String BRAND,
                                  @RequestParam String SKU,
                                  @RequestParam String NAME_EN,
                                  @RequestParam String NAME,
                                  @RequestParam int WEIGHT,
                                  @RequestParam int LENGTH,
                                  @RequestParam int WIDE,
                                  @RequestParam int HEIGHT,
                                  @RequestParam String SIZE,
                                  @RequestParam String COLOR_ORIG,
                                  @RequestParam String COLOR_RU,
                                  @RequestParam String DESCRIPTION,
                                  @RequestParam double PRICE,
                                  @RequestParam double PRICE_OLD
    ) {
        product.setBrand(BRAND);
        product.setHeight(HEIGHT);
        product.setColor_orig(COLOR_ORIG);
        product.setSku(SKU);
        product.setName_en(NAME_EN);
        product.setName(NAME);
        product.setWeight(WEIGHT);
        product.setLength(LENGTH);
        product.setWide(WIDE);
        product.setSize(SIZE);
        product.setColor_ru(COLOR_RU);
        product.setDescription(DESCRIPTION);
        product.setPrice(PRICE);
        product.setBackPrice(PRICE_OLD);
        productRepository.save(product);
        return "redirect:/admin";
    }

}
