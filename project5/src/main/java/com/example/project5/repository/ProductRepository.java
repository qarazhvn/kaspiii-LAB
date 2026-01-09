package com.example.project5.repository;

import com.example.project5.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Дополнительные методы поиска
    List<Product> findByNameContainingIgnoreCase(String name);
    
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
