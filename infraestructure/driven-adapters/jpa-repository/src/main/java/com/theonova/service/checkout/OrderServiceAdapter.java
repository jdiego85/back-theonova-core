package com.theonova.service.checkout;

import com.theonova.entities.checkout.Order;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.OrderGateway;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.checkout.OrderRepository;
import com.theonova.tables.checkout.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceAdapter implements OrderGateway {

    private final OrderRepository orderRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(this::entityToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUserId(long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public Order saveItem(Order item) {
        OrderEntity entity = item.id() > 0
                ? orderRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR))
                : new OrderEntity();

        entity.setOrderNumber(item.orderNumber());
        entity.setUserId(item.userId());
        entity.setWarehouse(warehouseRepository.getReferenceById(item.warehouseId()));
        entity.setStatus(item.status());
        entity.setPaymentStatus(item.paymentStatus());
        entity.setCurrency(item.currency());
        entity.setSubtotal(item.subtotal());
        entity.setShipping(item.shipping());
        entity.setTotal(item.total());
        entity.setShippingName(item.shippingName());
        entity.setShippingPhone(item.shippingPhone());
        entity.setShippingAddress(item.shippingAddress());
        entity.setNotes(item.notes());

        return entityToDomain(orderRepository.save(entity));
    }

    @Override
    public Order updateItem(Long id, Order request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }

    private Order entityToDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getOrderNumber(),
                entity.getUserId(),
                entity.getWarehouse().getId(),
                entity.getStatus(),
                entity.getPaymentStatus(),
                entity.getCurrency(),
                entity.getSubtotal(),
                entity.getShipping(),
                entity.getTotal(),
                entity.getShippingName(),
                entity.getShippingPhone(),
                entity.getShippingAddress(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
