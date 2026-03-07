package com.theonova.service.checkout;

import com.theonova.entities.checkout.Cart;
import com.theonova.enums.CartStatus;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.mappers.checkout.CartEntityMapper;
import com.theonova.repository.checkout.CartRepository;
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
    public Cart saveItem(Cart item) {
        return cartEntityMapper.entityToDomain(
                cartRepository.save(cartEntityMapper.domainToEntity(item))
        );
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
