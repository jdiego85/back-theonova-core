package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.OrderEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends RepositoryEngine<OrderEntity,Long> {
    @EntityGraph(attributePaths = {"warehouse"})
    Optional<OrderEntity> findByOrderNumber(String orderNumber);

    @EntityGraph(attributePaths = {"warehouse"})
    List<OrderEntity> findByUserId(Long userId);

    @EntityGraph(attributePaths = {"warehouse"})
    Optional<OrderEntity> findById(Long id);
}
