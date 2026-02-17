package com.theonova.service.inventory;

import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.gateways.inventory.ReorderSettingsGateway;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReorderSettingServiceAdapter implements ReorderSettingsGateway {
    @Override
    public Optional<ReorderSetting> findByProductAndWarehouse(long productId, long warehouseId) {
        return Optional.empty();
    }

    @Override
    public ReorderSetting saveItem(ReorderSetting item) {
        return null;
    }

    @Override
    public ReorderSetting updateItem(Long id, ReorderSetting request) {
        return null;
    }

    @Override
    public ReorderSetting findById(Long id) {
        return null;
    }

    @Override
    public List<ReorderSetting> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
