package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.payload.NewProductPayload;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products")
public class ProductsController {
    private final ProductService productService;

    @GetMapping("/list")
    public String catalogPage(Model model){
        model.addAttribute("products", this.productService.findAllProducts() );
        return "catalog/products/list";
    }
    @GetMapping("/create")
    public String getNewProductPage(){
        return "catalog/products/new_product";
    }
    @PostMapping("/create")
    public String createProduct(NewProductPayload payload){
        Product product = this.productService.save(payload.title(), payload.details());
        return "redirect:/catalog/products/%d".formatted(product.getId());
    }
}
