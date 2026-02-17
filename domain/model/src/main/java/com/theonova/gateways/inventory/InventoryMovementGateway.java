package com.theonova.gateways.inventory;

import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface InventoryMovementGateway extends ServiceManager<InventoryMovement,Long> {
    List<InventoryMovement> findByProductId(long productId);
    List<InventoryMovement> findByWarehouseId(long warehouseId);
}
