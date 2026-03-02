package com.theonova.repository.inventory;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.inventory.InventoryMovementEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface InventoryMovementRepository extends RepositoryEngine<InventoryMovementEntity,Long> {
    @EntityGraph(attributePaths = {"product", "warehouse"})
    List<InventoryMovementEntity> findByProduct_Id(Long productId);

    @EntityGraph(attributePaths = {"product", "warehouse"})
    List<InventoryMovementEntity> findByWarehouse_Id(Long warehouseId);

    @EntityGraph(attributePaths = {"product", "warehouse"})
    Optional<InventoryMovementEntity> findById(Long id);
}
