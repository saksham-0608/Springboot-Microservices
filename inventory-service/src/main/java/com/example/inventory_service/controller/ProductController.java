package com.example.inventory_service.controller;



import com.example.inventory_service.dto.ReserveRequest;
import com.example.inventory_service.entity.Product;
import com.example.inventory_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/{id}/reserve")
    public Product reserveProduct(
            @PathVariable Long id,
            @RequestBody ReserveRequest request) {

        return productService.reserveProduct(id, request.getQuantity());
    }

    @PostMapping("/{id}/release")
    public Product releaseProduct(
            @PathVariable Long id,
            @RequestBody ReserveRequest request) {

        return productService.releaseProduct(id, request.getQuantity());
    }
}
