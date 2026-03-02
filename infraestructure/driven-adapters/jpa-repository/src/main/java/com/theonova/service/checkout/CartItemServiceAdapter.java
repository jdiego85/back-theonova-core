package com.theonova.service.checkout;

import com.theonova.enums.ErrorCode;
import com.theonova.entities.checkout.CartItem;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.mappers.checkout.CartItemEntityMapper;
import com.theonova.repository.catalog.ProductRepository;
import com.theonova.repository.catalog.WarehouseRepository;
import com.theonova.repository.checkout.CartItemRepository;
import com.theonova.repository.checkout.CartRepository;
import com.theonova.tables.catalog.ProductEntity;
import com.theonova.tables.catalog.WarehouseEntity;
import com.theonova.tables.checkout.CartItemEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceAdapter implements CartItemGateway {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final CartItemEntityMapper mapper;

    @Override
    public List<CartItem> findByCartId(long cartId) {
        return cartItemRepository.findByCart_Id(cartId).stream()
                .map(mapper::entityToDomain)
                .toList();
    }

    @Override
    public void deleteByCartId(long cartId) {
        cartItemRepository.deleteByCart_Id(cartId);
    }

    @Override
    @Transactional
    public CartItem saveItem(CartItem item) {

        CartItemEntity entityToSave = mapper.domainToEntity(item);

        entityToSave.setCart(cartRepository.getReferenceById(item.cartId()));
        entityToSave.setProduct(productRepository.getReferenceById(item.productId()));
        entityToSave.setWarehouse(warehouseRepository.getReferenceById(item.warehouseId()));

        CartItemEntity savedEntity = cartItemRepository.save(entityToSave);
        return mapper.entityToDomain(savedEntity);
    }

    @Override
    public CartItem updateItem(Long id, CartItem request) {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id)
                .map(mapper::entityToDomain);
    }

    @Override
    public List<CartItem> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
