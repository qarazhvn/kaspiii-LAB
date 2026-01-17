package com.example.project8.controller;

import com.example.project8.dto.DeliveryRequest;
import com.example.project8.dto.DeliveryResponse;
import com.example.project8.service.DeliveryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
@Tag(name = "Delivery", description = "Delivery management API - микросервис доставки")
public class DeliveryController {
    
    private final DeliveryService deliveryService;
    
    @PostMapping
    @Operation(summary = "Create delivery", description = "Creates a new delivery request for a product. Body: { productId, address }")
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody DeliveryRequest request) {
        DeliveryResponse response = deliveryService.createDeliveryFromRequest(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all deliveries", description = "Retrieves a list of all deliveries")
    public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
        List<DeliveryResponse> deliveries = deliveryService.getAllDeliveries();
        return ResponseEntity.ok(deliveries);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get delivery by ID", description = "Retrieves a delivery by its ID")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id) {
        return deliveryService.getDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/product/{productId}")
    @Operation(summary = "Get deliveries by product ID", description = "Retrieves all deliveries for a specific product")
    public ResponseEntity<List<DeliveryResponse>> getDeliveriesByProductId(@PathVariable Long productId) {
        List<DeliveryResponse> deliveries = deliveryService.getDeliveriesByProductId(productId);
        return ResponseEntity.ok(deliveries);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update delivery status", description = "Updates the status of a delivery")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        try {
            DeliveryResponse response = deliveryService.updateDeliveryStatus(id, status);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete delivery", description = "Deletes a delivery by its ID")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryService.deleteDelivery(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
