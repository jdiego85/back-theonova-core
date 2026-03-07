package com.theonova.service.checkout;

import com.theonova.entities.checkout.OrderStatusHistory;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.OrderStatusHistoryGateway;
import com.theonova.mappers.checkout.OrderStatusHistoryEntityMapper;
import com.theonova.repository.checkout.OrderRepository;
import com.theonova.repository.checkout.OrderStatusHistoryRepository;
import com.theonova.tables.checkout.OrderStatusHistoryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryServiceAdapter implements OrderStatusHistoryGateway {

    private final OrderStatusHistoryRepository orderStatusHistoryRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderStatusHistory> findByOrderId(long orderId) {
        return orderStatusHistoryRepository.findByOrder_Id(orderId).stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public OrderStatusHistory saveItem(OrderStatusHistory item) {
        OrderStatusHistoryEntity entity = item.id() > 0
                ? orderStatusHistoryRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR))
                : mapper.domainToEntity(item);

        entity.setOrder(orderRepository.getReferenceById(item.orderId()));
        entity.setFromStatus(item.fromStatus());
        entity.setToStatus(item.toStatus());
        entity.setChangedBy(item.changedBy());
        entity.setChangedAt(item.changedAt() != null ? item.changedAt() : Instant.now());

        return mapper.entityToDomain(orderStatusHistoryRepository.save(entity));
    }

    @Override
    public OrderStatusHistory updateItem(Long id, OrderStatusHistory request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderStatusHistory> findById(Long id) {
        return orderStatusHistoryRepository.findById(id).map(mapper::entityToDomain);
    }

    @Override
    public List<OrderStatusHistory> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
