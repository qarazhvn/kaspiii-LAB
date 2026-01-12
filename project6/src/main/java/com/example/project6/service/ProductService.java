package com.example.project6.service;

import com.example.project6.entity.Product;
import com.example.project6.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    
    private final ProductRepository productRepository;
    private final DeliveryService deliveryService;
    
    // Create - Создать новый продукт и вызвать сервис доставки
    public Product createProduct(Product product) {
        log.info("Creating product: {}", product.getName());
        Product savedProduct = productRepository.save(product);
        
        // Вызываем сервис доставки если указан адрес
        if (product.getAddress() != null && !product.getAddress().isEmpty()) {
            log.info("Calling delivery service for product id: {}", savedProduct.getId());
            deliveryService.createDelivery(savedProduct.getId(), product.getAddress());
        }
        
        return savedProduct;
    }
    
    // Read - все продукты
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // Read - по ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    
    // Update
    public Product updateProduct(Long id, Product productDetails) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setAddress(productDetails.getAddress());
        
        return productRepository.save(product);
    }
    
    // Delete
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
    
    // Дополнительные методы
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Product> findByPriceRange(Double minPrice, Double maxPrice) {
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
}
