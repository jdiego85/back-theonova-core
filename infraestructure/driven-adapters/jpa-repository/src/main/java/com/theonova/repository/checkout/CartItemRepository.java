package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.CartItemEntity;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends RepositoryEngine<CartItemEntity,Long> {
    List<CartItemEntity> findByCart_Id(Long cartId);
    Optional<CartItemEntity> findByCart_IdAndProduct_IdAndWarehouse_Code(Long cartId, Long productId, String warehouseCode);
    void deleteByCart_Id(Long cartId);
}
