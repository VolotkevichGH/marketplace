package com.example.MarketplaceItaly.services;

import com.example.MarketplaceItaly.models.Category;
import com.example.MarketplaceItaly.models.Product;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Excel {


    @SneakyThrows
    public String createTableALL(List<Product> products) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Полный список товаров");

        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("URL_1");
        row.createCell(1).setCellValue("BRAND");
        row.createCell(2).setCellValue("SKU");
        row.createCell(3).setCellValue("NAME_EN");
        row.createCell(4).setCellValue("NAME");
        row.createCell(5).setCellValue("WEIGHT");
        row.createCell(6).setCellValue("LENGTH");
        row.createCell(7).setCellValue("WIDE");
        row.createCell(8).setCellValue("HEIGHT");
        row.createCell(9).setCellValue("SIZE");
        row.createCell(10).setCellValue("CAT1");
        row.createCell(11).setCellValue("CAT2");
        row.createCell(12).setCellValue("CAT3");
        row.createCell(13).setCellValue("COLOR_ORIG");
        row.createCell(14).setCellValue("COLOR_RU");
        row.createCell(15).setCellValue("DESCRIPTION");
        row.createCell(16).setCellValue("IMAGES");
        row.createCell(17).setCellValue("PRICE");
        row.createCell(18).setCellValue("PRICE_OLD");

        int i = 1;
        for (Product product : products) {
            ArrayList<Category> categories = new ArrayList<>(product.getCategory());
            Row row1 = sheet.createRow(i);
            row1.createCell(0).setCellValue(product.getURL_1());
            row1.createCell(1).setCellValue(product.getBrand());
            row1.createCell(2).setCellValue(product.getSku());
            row1.createCell(3).setCellValue(product.getName_en());
            row1.createCell(4).setCellValue(product.getName());
            row1.createCell(5).setCellValue(product.getWeight());
            row1.createCell(6).setCellValue(product.getLength());
            row1.createCell(7).setCellValue(product.getWide());
            row1.createCell(8).setCellValue(product.getHeight());
            row1.createCell(9).setCellValue(product.getSize());
            row1.createCell(10).setCellValue(String.valueOf(categories.getFirst()));
            row1.createCell(11).setCellValue(String.valueOf(categories.get(1)));
            row1.createCell(12).setCellValue(String.valueOf(categories.getLast()));
            row1.createCell(13).setCellValue(product.getColor_orig());
            row1.createCell(14).setCellValue(product.getColor_ru());
            row1.createCell(15).setCellValue(product.getDescription());
            row1.createCell(16).setCellValue(product.getImage().toString());
            row1.createCell(17).setCellValue(product.getPrice());
            row1.createCell(18).setCellValue(product.getBackPrice());
            i++;
        }
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        String text = date.format(formatter);
        String name = "Products-"+text+".xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(name);
        workbook.write(fileOutputStream);
        fileOutputStream.close();
        return name;
    }
}
