package com.theonova.application.port.out.inventory;

import com.theonova.domain.model.inventory.ReorderSetting;
import java.util.List;
import java.util.Optional;

public interface ReorderSettingsRepositoryPort {
    ReorderSetting save(ReorderSetting reorderSetting);

    Optional<ReorderSetting> findById(long id);

    Optional<ReorderSetting> findByProductAndWarehouse(long productId, long warehouseId);

    List<ReorderSetting> findAll();

    void deleteById(long id);
}
