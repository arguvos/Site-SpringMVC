package com.example.repositoryes;

import com.example.domain.Products;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductsRepository extends CrudRepository<Products, Integer> {

    List<Products> findByName(String name);

}
