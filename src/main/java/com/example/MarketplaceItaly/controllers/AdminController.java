package com.example.MarketplaceItaly.controllers;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import com.example.MarketplaceItaly.repositories.ProductRepository;
import com.example.MarketplaceItaly.services.telegram.TelegramBot;
import com.example.MarketplaceItaly.services.telegram.TelegramConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.awt.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductRepository productRepository;
    private final TelegramBot bot;
    private final TelegramConfig telegramConfig;


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
    public String postAddProduct(@RequestParam String name, @RequestParam String description, @RequestParam double price,
                                 @RequestParam String image) {
//        HashSet<Category> hashSet = new HashSet<>();
//        String[] massive = category.split(",");
//        for (String str : massive) {
//            str.trim();
//            hashSet.add(Category.valueOf(str));
//        }


        Product product = new Product();
        product.setName(name);
//        if (hashSet.contains(Category.men)) {
//            product.setHeadCategory(Category.men);
//        } else if (hashSet.contains(Category.women)) {
//            product.setHeadCategory(Category.women);
//        } else if (hashSet.contains(Category.child)) {
//            product.setHeadCategory(Category.child);
//        }
        product.setPrice(price);
        product.setImage(image);
        product.setDescription(description);
//        product.setCategory(hashSet);
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


    @SneakyThrows
    @PostMapping("/excel")
    public String enterToExcel() {
        LocalDateTime dateTime = LocalDateTime.now();
        String name = "Products-"+ dateTime.format(DateTimeFormatter.BASIC_ISO_DATE);
        String fullName = name + ".xlsx";
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(name);
            Row row = sheet.createRow(0);
            row.createCell(0).setCellValue("Название товара");  // Ячейка A1 со словом "Привет"
            row.createCell(1).setCellValue("Описание товара");  // И ячейка B1 с изложением "Мир"
            row.createCell(2).setCellValue("Цена");
            row.createCell(3).setCellValue("Ссылка на изображение");
            row.createCell(4).setCellValue("Первая категория");
            row.createCell(5).setCellValue("Вторая категория");
            row.createCell(6).setCellValue("Третья категория");
            int index = 1;
            for (Product product : productRepository.findAll()){
                Row row1 = sheet.createRow(index++);
                row1.createCell(0).setCellValue(product.getName());  // Ячейка A1 со словом "Привет"
                row1.createCell(1).setCellValue(product.getDescription());  // И ячейка B1 с изложением "Мир"
                row1.createCell(2).setCellValue(product.getPrice());
                row1.createCell(3).setCellValue(product.getImage());
                int i = 4;
                for (Category category : product.getCategory()){
                    row1.createCell(i++).setCellValue(category.toString());
                }
            }
            try (FileOutputStream out = new FileOutputStream(fullName)) {
                workbook.write(out);
            }  // Работа с файлом завершена, он закрыт
        }

        String message = "Товары на момент: " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        File file = new File(fullName);
        bot.sendFile(telegramConfig.getMyId(),file, message);

        return "redirect:/admin";
    }
}
