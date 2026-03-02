package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.CartItemEntity;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends RepositoryEngine<CartItemEntity,Long> {
    @EntityGraph(attributePaths = {"cart", "product", "warehouse"})
    List<CartItemEntity> findByCart_Id(Long cartId);

    @EntityGraph(attributePaths = {"cart", "product", "warehouse"})
    Optional<CartItemEntity> findByCart_IdAndProduct_IdAndWarehouse_Code(Long cartId, Long productId, String warehouseCode);

    @EntityGraph(attributePaths = {"cart", "product", "warehouse"})
    Optional<CartItemEntity> findById(Long id);

    void deleteByCart_Id(Long cartId);
}
