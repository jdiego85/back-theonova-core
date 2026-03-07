package com.theonova.repository.checkout;

import com.theonova.enums.CartStatus;
import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.CartEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends RepositoryEngine<CartEntity,Long > {
    List<CartEntity> findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CartEntity> findFirstByUserIdAndStatusOrderByIdDesc(Long userId, CartStatus status);
}
