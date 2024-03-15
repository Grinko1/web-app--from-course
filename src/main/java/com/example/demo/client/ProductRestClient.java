package com.example.demo.client;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRestClient {
    List<Product> findAllProducts(String filter);
    Product createProduct(String title, String details);
    Optional<Product> findProduct(int id);
    void updateProduct(int id, String title, String details);
    void deleteProduct(int id);
}
