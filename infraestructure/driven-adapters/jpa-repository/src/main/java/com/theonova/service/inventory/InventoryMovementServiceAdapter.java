package com.theonova.service.inventory;

import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.inventory.InventoryMovementGateway;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.inventory.InventoryMovementRepository;
import com.theonova.tables.inventory.InventoryMovementEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceAdapter implements InventoryMovementGateway {

    private final InventoryMovementRepository inventoryMovementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovement> findByProductId(long productId) {
        return inventoryMovementRepository.findByProduct_Id(productId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryMovement> findByWarehouseId(long warehouseId) {
        return inventoryMovementRepository.findByWarehouse_Id(warehouseId).stream()
                .map(this::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public InventoryMovement saveItem(InventoryMovement item) {
        InventoryMovementEntity entity = item.id() == null
                ? new InventoryMovementEntity()
                : inventoryMovementRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        entity.setProduct(productRepository.getReferenceById(item.productId()));
        entity.setWarehouse(warehouseRepository.getReferenceById(item.warehouseId()));
        entity.setMovementType(item.movementType());
        entity.setQuantity(item.quantity());
        entity.setRefType(item.refType());
        entity.setRefId(item.refId());
        entity.setNote(item.note());

        return entityToDomain(inventoryMovementRepository.save(entity));
    }

    @Override
    public InventoryMovement updateItem(Long id, InventoryMovement request) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InventoryMovement> findById(Long id) {
        return inventoryMovementRepository.findById(id)
                .map(this::entityToDomain);
    }

    @Override
    public List<InventoryMovement> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }

    private InventoryMovement entityToDomain(InventoryMovementEntity entity) {
        return InventoryMovement.builder()
                .id(entity.getId())
                .productId(entity.getProduct().getId())
                .warehouseId(entity.getWarehouse().getId())
                .movementType(entity.getMovementType())
                .quantity(entity.getQuantity())
                .refType(entity.getRefType())
                .refId(entity.getRefId())
                .note(entity.getNote())
                .build();
    }
}
