package com.theonova.gateways.inventory;

import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface InventoryBalanceGateway extends ServiceManager<InventoryBalance,Long> {
    Optional<InventoryBalance> findByProductAndWarehouse(long productId, long warehouseId);
    Optional<InventoryBalance> lockByProductAndWarehouse(long productId, long warehouseId);
}
