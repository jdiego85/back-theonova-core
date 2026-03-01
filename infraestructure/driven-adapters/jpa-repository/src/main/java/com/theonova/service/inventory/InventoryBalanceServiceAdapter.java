package com.theonova.service.inventory;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.enums.ErrorCode;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.mappers.inventory.InventoryBalanceEntityMapper;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.inventory.InventoryBalanceRepository;
import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.inventory.InventoryBalanceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryBalanceServiceAdapter implements InventoryBalanceGateway {

    private final InventoryBalanceRepository inventoryBalanceRepository;
    private final InventoryBalanceEntityMapper mapper;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Optional<InventoryBalance> findByProductAndWarehouse(long productId, long warehouseId) {
        return Optional.empty();
    }

    @Override
    public Optional<InventoryBalance> lockByProductAndWarehouse(long productId, long warehouseId) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public InventoryBalance saveItem(InventoryBalance item) {
        InventoryBalanceEntity inventoryBalanceEntity = mapper.domainToEntity(item);

        ProductEntity productRef = productRepository.getReferenceById(item.productId());
        WarehouseEntity warehouseRef = warehouseRepository.getReferenceById(item.warehouseId());

        inventoryBalanceEntity.setProduct(productRef);
        inventoryBalanceEntity.setWarehouse(warehouseRef);

        InventoryBalanceEntity savedEntity = inventoryBalanceRepository.save(inventoryBalanceEntity);
        return mapper.entityToDomain(savedEntity);
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
