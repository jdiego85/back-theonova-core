package com.theonova.service.inventory;

import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryBalanceServiceAdapter implements InventoryBalanceGateway {
    @Override
    public Optional<InventoryBalance> findByProductAndWarehouse(long productId, long warehouseId) {
        return Optional.empty();
    }

    @Override
    public Optional<InventoryBalance> lockByProductAndWarehouse(long productId, long warehouseId) {
        return Optional.empty();
    }

    @Override
    public InventoryBalance saveItem(InventoryBalance item) {
        return null;
    }

    @Override
    public InventoryBalance updateItem(Long id, InventoryBalance request) {
        return null;
    }

    @Override
    public Optional<InventoryBalance> findById(Long id) {
        return null;
    }

    @Override
    public List<InventoryBalance> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}