package com.example.inventory_service.service;

import com.example.inventory_service.entity.Product;
import com.example.inventory_service.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }



    @Transactional
    public Product reserveProduct(Long id, Integer quantity) {

        Product product = getProductById(id);

        if (product.getAvailableQuantity() < quantity) {
            throw new RuntimeException("Insufficient inventory");
        }

        product.setAvailableQuantity(
                product.getAvailableQuantity() - quantity
        );

        return productRepository.save(product);
    }

    @Transactional
    public Product releaseProduct(Long id, Integer quantity) {

        Product product = getProductById(id);

        product.setAvailableQuantity(
                product.getAvailableQuantity() + quantity
        );

        return productRepository.save(product);
    }
}
