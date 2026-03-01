package com.theonova.repository.inventory;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.inventory.ReorderSettingEntity;

import java.util.Optional;

public interface ReorderSettingRepository extends RepositoryEngine<ReorderSettingEntity,Long> {
    Optional<ReorderSettingEntity> findByProduct_IdAndWarehouse_Id(long productId, long warehouseId);
    Optional<ReorderSettingEntity> findByProduct_IdAndWarehouse_Code(long productId, String warehouseCode);
}
