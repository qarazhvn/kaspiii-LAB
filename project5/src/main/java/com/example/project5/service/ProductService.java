package com.example.project5.service;

import com.example.project5.entity.Product;
import com.example.project5.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository productRepository;
    
    // Create
    public Product createProduct(Product product) {
        return productRepository.save(product);
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
