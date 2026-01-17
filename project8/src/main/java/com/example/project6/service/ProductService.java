package com.example.project8.service;

import com.example.project8.entity.Product;
import com.example.project8.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final DeliveryService deliveryService;

    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        Product savedProduct = productRepository.save(product);

        if (product.getAddress() != null && !product.getAddress().isEmpty()) {
            log.info("Calling delivery service for product id: {}", savedProduct.getId());
            deliveryService.createDelivery(savedProduct.getId(), product.getAddress());
        }

        return savedProduct;
    }

    // 🔥 ASYNC METHOD
    @Async
    public CompletableFuture<List<Product>> getAllProducts() {
        log.info("Fetching all products asynchronously");
        List<Product> products = productRepository.findAll();
        return CompletableFuture.completedFuture(products);
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setAddress(productDetails.getAddress());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
