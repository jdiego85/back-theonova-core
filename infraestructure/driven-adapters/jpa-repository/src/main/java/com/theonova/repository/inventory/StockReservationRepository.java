package com.theonova.repository.inventory;

import com.theonova.enums.ReservationStatus;
import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.inventory.StockReservationEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface StockReservationRepository extends RepositoryEngine<StockReservationEntity,Long> {
    @EntityGraph(attributePaths = {"cart", "order", "product", "warehouse"})
    List<StockReservationEntity> findByCart_Id(Long cartId);

    @EntityGraph(attributePaths = {"cart", "order", "product", "warehouse"})
    List<StockReservationEntity> findByOrder_Id(Long orderId);

    @EntityGraph(attributePaths = {"cart", "order", "product", "warehouse"})
    Optional<StockReservationEntity> findByCart_IdAndProduct_IdAndWarehouse_IdAndStatus(Long cartId, Long productId,
            Long warehouseId, ReservationStatus status);

    @EntityGraph(attributePaths = {"cart", "order", "product", "warehouse"})
    Optional<StockReservationEntity> findById(Long id);
}
