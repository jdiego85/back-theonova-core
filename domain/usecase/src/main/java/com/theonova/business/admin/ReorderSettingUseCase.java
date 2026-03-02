package com.theonova.business.admin;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.inventory.ReorderSetting;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.inventory.ReorderSettingsGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReorderSettingUseCase {

    private final ReorderSettingsGateway reorderSettingsGateway;
    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;

    public ReorderSetting execute(ReorderSetting reorderSetting) {
        Product product = productGateway.findBySku(reorderSetting.skuProduct())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(reorderSetting.codeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        ReorderSetting toSave = reorderSetting.toBuilder()
                .productId(product.id())
                .warehouseId(warehouse.id())
                .build();
        return reorderSettingsGateway.saveItem(toSave);
    }
}
