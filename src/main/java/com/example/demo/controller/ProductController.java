package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.payload.UpdateProductPayload;
import com.example.demo.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/catalog/products/{productId:\\d+}")
public class ProductController {
    private final ProductService productService;
    private final MessageSource messageSource;

    @ModelAttribute("product")
    public Product product(@PathVariable("productId") int productId) {
        return this.productService.findProductById(productId).orElseThrow(
                () -> new NoSuchElementException("catalog.errors.product.not_found")
        );
    }


    @GetMapping
    public String productDetails() {
        return "catalog/products/product";
    }

    @GetMapping("/edit")
    public String editPage() {
        return "catalog/products/edit";
    }

    @PostMapping("edit")
    public String updateProduct(@ModelAttribute("product") Product product, UpdateProductPayload payload) {

        productService.updateProduct(product.getId(), payload.title(), payload.details());
        return "redirect:/catalog/products/%d".formatted(product.getId());
    }

    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product) {
        productService.deleteProduct(product.getId());
        return "redirect:/catalog/products/list";

    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElementException(NoSuchElementException exception, Model model, HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("errors", this.messageSource.getMessage(exception.getMessage(), new Object[0], exception.getMessage(), locale));
        return "/errors/404";
    }
}
