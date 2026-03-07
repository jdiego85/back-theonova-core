package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.OrderStatusHistoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface OrderStatusHistoryRepository extends RepositoryEngine<OrderStatusHistoryEntity,Long> {
    @EntityGraph(attributePaths = {"order"})
    List<OrderStatusHistoryEntity> findByOrder_Id(Long orderId);

    @EntityGraph(attributePaths = {"order"})
    Optional<OrderStatusHistoryEntity> findById(Long id);
}
