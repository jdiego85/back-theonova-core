package com.theonova.application.port.out.checkout;

import com.theonova.domain.model.inventory.StockReservation;
import java.util.List;
import java.util.Optional;

public interface StockReservationRepositoryPort {
    StockReservation save(StockReservation stockReservation);

    Optional<StockReservation> findById(long id);

    List<StockReservation> findByCartId(long cartId);

    List<StockReservation> findByOrderId(long orderId);

    List<StockReservation> findAll();
}
