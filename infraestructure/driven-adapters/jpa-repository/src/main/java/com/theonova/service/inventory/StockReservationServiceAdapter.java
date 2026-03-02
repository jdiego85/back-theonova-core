package com.theonova.service.inventory;

import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.inventory.StockReservationGateway;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.checkout.CartRepository;
import com.theonova.repository.checkout.OrderRepository;
import com.theonova.repository.inventory.StockReservationRepository;
import com.theonova.tables.inventory.StockReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockReservationServiceAdapter implements StockReservationGateway {
    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");

    private final StockReservationRepository stockReservationRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;

    @Override
    @Transactional(readOnly = true)
    public List<StockReservation> findByCartId(long cartId) {
        return stockReservationRepository.findByCart_Id(cartId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockReservation> findByOrderId(long orderId) {
        return stockReservationRepository.findByOrder_Id(orderId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public StockReservation saveItem(StockReservation item) {
        StockReservationEntity entity = item.id() != null
                ? stockReservationRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR))
                : new StockReservationEntity();

        entity.setProduct(productRepository.getReferenceById(item.productId()));
        entity.setWarehouse(warehouseRepository.getReferenceById(item.warehouseId()));
        entity.setOrder(item.orderId() == null ? null : orderRepository.getReferenceById(item.orderId()));
        entity.setCart(item.cartId() == null ? null : cartRepository.getReferenceById(item.cartId()));
        entity.setQuantity(item.quantity());
        entity.setStatus(item.status());
        entity.setExpiresAt(LocalDateTime.now(GUAYAQUIL_ZONE).plusMinutes(30));

        StockReservationEntity savedEntity = stockReservationRepository.save(entity);
        return entityToDomain(savedEntity);
    }

    @Override
    public StockReservation updateItem(Long id, StockReservation request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StockReservation> findById(Long id) {
        return stockReservationRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<StockReservation> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }

    private StockReservation entityToDomain(StockReservationEntity entity) {
        return new StockReservation(
                entity.getId(),
                entity.getProduct().getId(),
                entity.getWarehouse().getId(),
                entity.getOrder() == null ? null : entity.getOrder().getId(),
                entity.getCart() == null ? null : entity.getCart().getId(),
                entity.getQuantity(),
                entity.getStatus()
        );
    }
}
