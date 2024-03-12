package com.example.demo.service;

import com.example.demo.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAllProducts();
    public Product save(String title, String details);

   public Optional<Product> findProductById(int productId);

    void updateProduct(Integer id, String title, String details);

    void deleteProduct(Integer id);
}
