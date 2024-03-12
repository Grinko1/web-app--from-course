package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.IntStream;

@Repository
public class InMemoryProductRepository implements ProductRepository{
    private final List<Product> products = Collections.synchronizedList(new LinkedList<>());

    public InMemoryProductRepository() {
        IntStream.range(1,4)
                .forEach(i -> this.products.add(new Product(i, "Товар №%d".formatted(i),
                        "Описание товара №%d".formatted(i))));
    }

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(products);
    }

    @Override
    public Product createProduct(Product product) {
        product.setId(this.products.stream()
                .max(Comparator.comparingInt(Product::getId)).
                map(Product::getId)
                .orElse(0) + 1);

        products.add(product);
        return product;
    }

    @Override
    public Optional<Product> findById(Integer productId) {
        return this.products.stream().filter(i -> Objects.equals(productId , i.getId())).findFirst();
    }

    @Override
    public void deleteById(Integer id) {
        this.products.removeIf(i -> Objects.equals(i.getId(), id));
    }

}
