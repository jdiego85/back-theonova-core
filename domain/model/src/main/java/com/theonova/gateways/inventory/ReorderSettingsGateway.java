package com.theonova.gateways.inventory;

import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.gateways.ServiceManager;

import java.util.Optional;

public interface ReorderSettingsGateway extends ServiceManager<ReorderSetting,Long> {
    Optional<ReorderSetting> findByProductAndWarehouse(long productId, long warehouseId);
}
