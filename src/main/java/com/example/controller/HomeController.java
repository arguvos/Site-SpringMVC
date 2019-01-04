package com.example.controller;

import com.example.domain.Products;
import com.example.repositoryes.ProductsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@Controller
public class HomeController {

    @Autowired
    private ProductsRepository productsRepository;
    private static final Logger logger = LogManager.getLogger();

    @GetMapping("/")
    public String home(Map<String, Object> model) {
        return "home";
    }

    @GetMapping("/catalog")
    public String catalog(Map<String, Object> model) {
        Iterable<Products> products = productsRepository.findAll();
        model.put("products", products);
        logger.info("Get catalog");
        return "catalog";
    }

    @PostMapping("/catalog")
    public String add(@RequestParam String name, @RequestParam String description, Map<String, Object> model) {
        Products product = new Products(name, description);
        productsRepository.save(product);

        Iterable<Products> products = productsRepository.findAll();
        model.put("products", products);
        logger.info("Add new productions to catalog: name {}, description {}", name, description);
        return "catalog";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Products> products;

        if (filter != null && !filter.isEmpty()) {
            products = productsRepository.findByName(filter);
        } else {
            products= productsRepository.findAll();
        }

        model.put("products", products);
        logger.info("Filter productions by name: {}", filter);
        return "catalog";
    }
}
