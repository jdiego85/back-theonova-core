package com.theonova.application.port.out.inventory;

import com.theonova.domain.model.inventory.InventoryBalance;
import java.util.List;
import java.util.Optional;

public interface InventoryBalanceRepositoryPort {
    InventoryBalance save(InventoryBalance inventoryBalance);

    Optional<InventoryBalance> findById(long id);

    Optional<InventoryBalance> findByProductAndWarehouse(long productId, long warehouseId);

    Optional<InventoryBalance> lockByProductAndWarehouse(long productId, long warehouseId);

    List<InventoryBalance> findAll();

    void deleteById(long id);
}
