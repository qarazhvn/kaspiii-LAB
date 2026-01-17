package com.example.project8.service;

import com.example.project8.dto.DeliveryRequest;
import com.example.project8.dto.DeliveryResponse;
import com.example.project8.entity.Delivery;
import com.example.project8.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
    
    private final DeliveryRepository deliveryRepository;
    private final WebClient webClient;
    
    /**
     * Создать доставку
     * Демонстрирует использование WebClient для вызова внешних сервисов
     */
    public DeliveryResponse createDelivery(Long productId, String address) {
        log.info("Creating delivery for product id: {} to address: {}", productId, address);
        
        Delivery delivery = Delivery.builder()
                .productId(productId)
                .address(address)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        
        Delivery savedDelivery = deliveryRepository.save(delivery);
        
        // Демонстрация WebClient для вызова внешних сервисов
        // Здесь можно было бы вызвать реальный микросервис доставки
        callExternalDeliveryService(savedDelivery);
        
        return DeliveryResponse.from(savedDelivery);
    }
    
    /**
     * Альтернативный способ создания доставки через API
     */
    public DeliveryResponse createDeliveryFromRequest(DeliveryRequest request) {
        return createDelivery(request.getProductId(), request.getAddress());
    }
    
    /**
     * Получить все доставки
     */
    public List<DeliveryResponse> getAllDeliveries() {
        return deliveryRepository.findAll()
                .stream()
                .map(DeliveryResponse::from)
                .toList();
    }
    
    /**
     * Получить доставку по ID
     */
    public Optional<DeliveryResponse> getDeliveryById(Long id) {
        return deliveryRepository.findById(id)
                .map(DeliveryResponse::from);
    }
    
    /**
     * Получить доставки по productId
     */
    public List<DeliveryResponse> getDeliveriesByProductId(Long productId) {
        return deliveryRepository.findByProductId(productId)
                .stream()
                .map(DeliveryResponse::from)
                .toList();
    }
    
    /**
     * Обновить статус доставки
     */
    public DeliveryResponse updateDeliveryStatus(Long id, String status) {
        Delivery delivery = deliveryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Delivery not found with id: " + id));
        
        delivery.setStatus(status);
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        
        return DeliveryResponse.from(updatedDelivery);
    }
    
    /**
     * Удалить доставку
     */
    public void deleteDelivery(Long id) {
        if (!deliveryRepository.existsById(id)) {
            throw new RuntimeException("Delivery not found with id: " + id);
        }
        deliveryRepository.deleteById(id);
    }
    
    /**
     * Демонстрация WebClient для асинхронного вызова внешних сервисов
     * WebClient - рекомендуемый способ для новых приложений
     */
    private void callExternalDeliveryService(Delivery delivery) {
        try {
            // Пример асинхронного вызова внешнего сервиса
            // Здесь можно было бы вызвать реальный микросервис доставки
            log.info("WebClient: Sending delivery request for product: {}", delivery.getProductId());
            
            // Неблокирующий вызов (демонстрация)
            webClient.post()
                    .uri("http://localhost:8081/api/delivery") // Адрес внешнего сервиса
                    .bodyValue(delivery)
                    .retrieve()
                    .toEntity(Void.class)
                    .subscribe(
                        response -> log.info("External service responded with status: {}", response.getStatusCode()),
                        error -> log.error("Error calling external service: {}", error.getMessage())
                    );
        } catch (Exception e) {
            log.error("Could not reach external delivery service: {}", e.getMessage());
            // Не выбрасываем исключение, чтобы процесс создания доставки не упал
        }
    }
}
