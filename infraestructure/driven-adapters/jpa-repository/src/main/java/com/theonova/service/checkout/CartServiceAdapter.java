package com.theonova.service.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.CartStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.mappers.checkout.CartEntityMapper;
import com.theonova.repository.checkout.CartRepository;
import com.theonova.tables.checkout.CartEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceAdapter implements CartGateway {

    private final CartRepository cartRepository;
    private final CartEntityMapper cartEntityMapper;

    @Override
    public List<Cart> findByUserId(long userId) {
        return cartRepository.findByUserId(userId)
                .stream()
                .map(cartEntityMapper::entityToDomain)
                .toList();
    }

    @Override
    @Transactional
    public Optional<Cart> lockActiveByUserId(long userId) {
        return cartRepository.findFirstByUserIdAndStatusOrderByIdDesc(userId, CartStatus.ACTIVE)
                .map(cartEntityMapper::entityToDomain);
    }

    @Override
    @Transactional
    public Cart saveItem(Cart item) {
        CartEntity entityToSave = item.id() == null
                ? cartEntityMapper.domainToEntity(item)
                : cartRepository.findById(item.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        entityToSave.setUserId(item.userId());
        entityToSave.setStatus(item.status());
        entityToSave.setLastActivityAt(cartEntityMapper.map(item.lastActivityAt()));
        entityToSave.setAbandonedAt(cartEntityMapper.map(item.abandonedAt()));

        return cartEntityMapper.entityToDomain(cartRepository.save(entityToSave));
    }

    @Override
    public Cart updateItem(Long id, Cart request) {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return cartRepository.findById(id)
                .map(cartEntityMapper::entityToDomain);
    }

    @Override
    public List<Cart> findAll() {
        return List.of();
    }

    @Override
    public void removeItem(Long id) {

    }
}
