package com.theonova.repository.inventory;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.inventory.InventoryBalanceEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryBalanceRepository extends RepositoryEngine<InventoryBalanceEntity,Long> {
    @EntityGraph(attributePaths = {"product", "warehouse"})
    Optional<InventoryBalanceEntity> findByProduct_IdAndWarehouse_Id(Long productId, Long warehouseId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @EntityGraph(attributePaths = {"product", "warehouse"})
    @Query("SELECT ib FROM InventoryBalanceEntity ib WHERE ib.product.id = :productId AND ib.warehouse.id = :warehouseId")
    Optional<InventoryBalanceEntity> lockByProductAndWarehouse(@Param("productId") Long productId,
            @Param("warehouseId") Long warehouseId);
}
