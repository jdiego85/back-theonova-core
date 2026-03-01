package com.theonova.repository.checkout;

import com.theonova.repository.RepositoryEngine;
import com.theonova.tables.checkout.CartEntity;

import java.util.List;

public interface CartRepository extends RepositoryEngine<CartEntity,Long > {
    List<CartEntity> findByUserId(Long userId);
}
