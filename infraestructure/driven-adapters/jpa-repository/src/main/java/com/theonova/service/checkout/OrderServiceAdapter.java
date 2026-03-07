package com.theonova.service.checkout;

import com.theonova.entities.checkout.Order;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.OrderGateway;
import com.theonova.mappers.checkout.OrderEntityMapper;
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
    private final OrderEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber)
                .map(mapper::entityToDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> findByUserId(long userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public Order saveItem(Order item) {
        OrderEntity entity = item.id() > 0
                ? orderRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR))
                : mapper.domainToEntity(item);

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

        return mapper.entityToDomain(orderRepository.save(entity));
    }

    @Override
    public Order updateItem(Long id, Order request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id).map(mapper::entityToDomain);
    }

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
