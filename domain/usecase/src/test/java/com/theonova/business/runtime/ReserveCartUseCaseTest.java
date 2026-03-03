package com.theonova.business.runtime;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.entities.runtime.ReserveCart;
import com.theonova.enums.CartStatus;
import com.theonova.enums.InventoryMovementType;
import com.theonova.enums.ReservationStatus;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.gateways.inventory.InventoryMovementGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReserveCartUseCaseTest {

    @Mock
    private ProductGateway productGateway;
    @Mock
    private WarehouseGateway warehouseGateway;
    @Mock
    private CartGateway cartGateway;
    @Mock
    private CartItemGateway cartItemGateway;
    @Mock
    private InventoryBalanceGateway inventoryBalanceGateway;
    @Mock
    private StockReservationGateway stockReservationGateway;
    @Mock
    private InventoryMovementGateway inventoryMovementGateway;

    @InjectMocks
    private ReserveCartUseCase reserveCartUseCase;

    @Test
    void executeShouldReserveOnlyDeltaWhenReservationAlreadyExists() {
        Product product = Product.builder().id(10L).sku("SKU-100").build();
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(5)
                .build();
        InventoryBalance lockedBalance = InventoryBalance.builder()
                .id(88L)
                .productId(10L)
                .warehouseId(20L)
                .onHand(10)
                .reserved(6)
                .build();
        StockReservation existingReservation = StockReservation.builder()
                .id(44L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(3)
                .status(ReservationStatus.ACTIVE)
                .build();

        ReserveCart request = ReserveCart.builder()
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .userId(99L)
                .build();

        when(productGateway.findBySku("SKU-100")).thenReturn(Optional.of(product));
        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.findByUserId(99L)).thenReturn(List.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(cartItem));
        when(inventoryBalanceGateway.lockByProductAndWarehouse(10L, 20L)).thenReturn(Optional.of(lockedBalance));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of(existingReservation));
        when(inventoryBalanceGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockReservationGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(inventoryMovementGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));

        reserveCartUseCase.execute(request);

        ArgumentCaptor<InventoryBalance> balanceCaptor = ArgumentCaptor.forClass(InventoryBalance.class);
        verify(inventoryBalanceGateway).saveItem(balanceCaptor.capture());
        assertEquals(8, balanceCaptor.getValue().reserved());

        ArgumentCaptor<InventoryMovement> movementCaptor = ArgumentCaptor.forClass(InventoryMovement.class);
        verify(inventoryMovementGateway).saveItem(movementCaptor.capture());
        assertEquals(InventoryMovementType.RESERVE, movementCaptor.getValue().movementType());
        assertEquals(2, movementCaptor.getValue().quantity());

        ArgumentCaptor<StockReservation> reservationCaptor = ArgumentCaptor.forClass(StockReservation.class);
        verify(stockReservationGateway).saveItem(reservationCaptor.capture());
        assertEquals(5, reservationCaptor.getValue().quantity());
    }

    @Test
    void executeShouldReleaseWhenTargetQuantityIsLowerThanCurrentReservation() {
        Product product = Product.builder().id(10L).sku("SKU-100").build();
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(2)
                .build();
        InventoryBalance lockedBalance = InventoryBalance.builder()
                .id(88L)
                .productId(10L)
                .warehouseId(20L)
                .onHand(10)
                .reserved(7)
                .build();
        StockReservation existingReservation = StockReservation.builder()
                .id(44L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(5)
                .status(ReservationStatus.ACTIVE)
                .build();

        ReserveCart request = ReserveCart.builder()
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .userId(99L)
                .build();

        when(productGateway.findBySku("SKU-100")).thenReturn(Optional.of(product));
        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.findByUserId(99L)).thenReturn(List.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(cartItem));
        when(inventoryBalanceGateway.lockByProductAndWarehouse(10L, 20L)).thenReturn(Optional.of(lockedBalance));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of(existingReservation));
        when(inventoryBalanceGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockReservationGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(inventoryMovementGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));

        reserveCartUseCase.execute(request);

        ArgumentCaptor<InventoryBalance> balanceCaptor = ArgumentCaptor.forClass(InventoryBalance.class);
        verify(inventoryBalanceGateway).saveItem(balanceCaptor.capture());
        assertEquals(4, balanceCaptor.getValue().reserved());

        ArgumentCaptor<InventoryMovement> movementCaptor = ArgumentCaptor.forClass(InventoryMovement.class);
        verify(inventoryMovementGateway).saveItem(movementCaptor.capture());
        assertEquals(InventoryMovementType.RELEASE, movementCaptor.getValue().movementType());
        assertEquals(3, movementCaptor.getValue().quantity());

        ArgumentCaptor<StockReservation> reservationCaptor = ArgumentCaptor.forClass(StockReservation.class);
        verify(stockReservationGateway).saveItem(reservationCaptor.capture());
        assertEquals(2, reservationCaptor.getValue().quantity());
    }
}
