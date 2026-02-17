package com.theonova.service.inventory;

import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.gateways.inventory.InventoryMovementGateway;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryMovementServiceAdapter implements InventoryMovementGateway {
    @Override
    public List<InventoryMovement> findByProductId(long productId) {
        return List.of();
    }

    @Override
    public List<InventoryMovement> findByWarehouseId(long warehouseId) {
        return List.of();
    }

    @Override
    public InventoryMovement saveItem(InventoryMovement item) {
        return null;
    }

    @Override
    public InventoryMovement updateItem(Long id, InventoryMovement request) {
        return null;
    }

    @Override
    public InventoryMovement findById(Long id) {
        return null;
    }

    @Override
    public List<InventoryMovement> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
