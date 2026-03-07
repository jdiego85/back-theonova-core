package com.theonova.service.checkout;

import com.theonova.entities.checkout.OrderItem;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.OrderItemGateway;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.checkout.OrderItemRepository;
import com.theonova.repository.checkout.OrderRepository;
import com.theonova.tables.checkout.OrderItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemServiceAdapter implements OrderItemGateway {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findByOrderId(long orderId) {
        return orderItemRepository.findByOrder_Id(orderId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderItem> findByProductId(long productId) {
        return orderItemRepository.findByProduct_Id(productId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public OrderItem saveItem(OrderItem item) {
        OrderItemEntity entity = item.id() > 0
                ? orderItemRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR))
                : new OrderItemEntity();

        entity.setOrder(orderRepository.getReferenceById(item.orderId()));
        entity.setProduct(productRepository.getReferenceById(item.productId()));
        entity.setQuantity(item.quantity());
        entity.setUnitPrice(item.unitPrice());
        entity.setLineTotal(item.lineTotal());

        return entityToDomain(orderItemRepository.save(entity));
    }

    @Override
    public OrderItem updateItem(Long id, OrderItem request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderItem> findById(Long id) {
        return orderItemRepository.findById(id).map(this::entityToDomain);
    }

    @Override
    public List<OrderItem> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }

    private OrderItem entityToDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getId(),
                entity.getOrder().getId(),
                entity.getProduct().getId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getLineTotal(),
                entity.getCreatedAt()
        );
    }
}
