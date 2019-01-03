package com.example;

import com.example.domain.Products;
import com.example.repositoryes.ProductsRepository;
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

    @GetMapping
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Map<String, Object> model) {
        model.put("name", name);
        return "home";
    }

    @GetMapping("/catalog")
    public String catalog(Map<String, Object> model) {
        Iterable<Products> products = productsRepository.findAll();
        model.put("products", products);
        return "catalog";
    }

    @PostMapping("/catalog")
    public String add(@RequestParam String name, @RequestParam String description, Map<String, Object> model) {
        Products product = new Products(name, description);
        productsRepository.save(product);

        Iterable<Products> products = productsRepository.findAll();
        model.put("products", products);
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
        return "catalog";
    }
}
