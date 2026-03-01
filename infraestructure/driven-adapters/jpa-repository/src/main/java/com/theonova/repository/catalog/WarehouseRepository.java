package com.theonova.repository.catalog;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.catalog.WarehouseEntity;

import java.util.Optional;

public interface WarehouseRepository extends RepositoryEngine<WarehouseEntity,Long> {

    Optional<WarehouseEntity> findByCode(String code);
}
