package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.inventory.InventoryMovement;
import java.util.List;
import java.util.Optional;

public interface InventoryMovementRepositoryPort {
    InventoryMovement save(InventoryMovement inventoryMovement);

    Optional<InventoryMovement> findById(long id);

    List<InventoryMovement> findByProductId(long productId);

    List<InventoryMovement> findByWarehouseId(long warehouseId);
}
