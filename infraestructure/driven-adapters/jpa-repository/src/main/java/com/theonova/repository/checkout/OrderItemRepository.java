package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.OrderItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends RepositoryEngine<OrderItemEntity, Long> {
    @EntityGraph(attributePaths = {"order", "product"})
    List<OrderItemEntity> findByOrder_Id(Long orderId);

    @EntityGraph(attributePaths = {"order", "product"})
    List<OrderItemEntity> findByProduct_Id(Long productId);

    @EntityGraph(attributePaths = {"order", "product"})
    Optional<OrderItemEntity> findById(Long id);
}
