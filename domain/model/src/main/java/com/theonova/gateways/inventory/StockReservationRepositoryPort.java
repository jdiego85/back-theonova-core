package com.theonova.gateways.inventory;

import com.theonova.entities.inventory.StockReservation;
import com.theonova.gateways.ServiceManager;

import java.util.List;

public interface StockReservationRepositoryPort extends ServiceManager<StockReservation,Long> {
    List<StockReservation> findByCartId(long cartId);
    List<StockReservation> findByOrderId(long orderId);
}
