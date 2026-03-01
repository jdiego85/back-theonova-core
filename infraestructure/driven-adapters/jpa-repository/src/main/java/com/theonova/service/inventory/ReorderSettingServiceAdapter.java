package com.theonova.service.inventory;

import com.theonova.enums.ErrorCode;
import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.inventory.ReorderSettingsGateway;
import com.theonova.mappers.inventory.ReorderSettingEntityMapper;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.inventory.ReorderSettingRepository;
import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.inventory.ReorderSettingEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReorderSettingServiceAdapter implements ReorderSettingsGateway {

    private final ReorderSettingRepository reorderSettingRepository;
    private final ReorderSettingEntityMapper mapper;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public Optional<ReorderSetting> findByProductAndWarehouse(long productId, long warehouseId) {
        return reorderSettingRepository.findByProduct_IdAndWarehouse_Id(productId, warehouseId)
                .map(mapper::entityToDomain);
    }

    @Override
    @Transactional
    public ReorderSetting saveItem(ReorderSetting item) {
        ReorderSettingEntity reorderSettingEntity = mapper.domainToEntity(item);

        ProductEntity productRef = productRepository.getReferenceById(item.productId());
        WarehouseEntity warehouseRef = warehouseRepository.getReferenceById(item.warehouseId());

        reorderSettingEntity.setProduct(productRef);
        reorderSettingEntity.setWarehouse(warehouseRef);

        ReorderSettingEntity savedEntity = reorderSettingRepository.save(reorderSettingEntity);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public ReorderSetting updateItem(Long id, ReorderSetting request) {
        return null;
    }

    @Override
    public Optional<ReorderSetting> findById(Long id) {
        return reorderSettingRepository.findById(id)
                .map(mapper::entityToDomain);
    }

    @Override
    public List<ReorderSetting> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
