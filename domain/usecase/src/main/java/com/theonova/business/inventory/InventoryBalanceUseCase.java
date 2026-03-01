package com.theonova.business.inventory;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.enums.ErrorCode;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InventoryBalanceUseCase {

    private final InventoryBalanceGateway  inventoryBalanceGateway;
    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;

    public InventoryBalance execute(InventoryBalance inventoryBalance) {
        Product product = productGateway.findBySku(inventoryBalance.skuProduct())
                .orElseThrow(()-> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(inventoryBalance.codeWarehouse())
                .orElseThrow(()-> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));
        InventoryBalance toSave = inventoryBalance.toBuilder()
                .productId(product.id())
                .warehouseId(warehouse.id())
                .build();
        return inventoryBalanceGateway.saveItem(toSave);
    }
}
