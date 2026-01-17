package com.example.project8.repository;

import com.example.project8.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    List<Delivery> findByProductId(Long productId);
    
    List<Delivery> findByStatus(String status);
}
