package com.theonova.business.checkout;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartItemUseCaseTest {

    @Mock
    private CartItemGateway cartItemGateway;
    @Mock
    private CartGateway cartGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private WarehouseGateway warehouseGateway;
    @Mock
    private InventoryBalanceGateway inventoryBalanceGateway;
    @Mock
    private StockReservationGateway stockReservationGateway;

    @InjectMocks
    private CartItemUseCase cartItemUseCase;

    @Test
    void executeShouldAllowWhenAvailableIncludesOwnActiveReservation() {
        Product product = Product.builder().id(10L).sku("SKU-100").build();
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem existingItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(5)
                .build();
        StockReservation ownReservation = StockReservation.builder()
                .id(44L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(5)
                .status(ReservationStatus.ACTIVE)
                .build();
        InventoryBalance balance = InventoryBalance.builder()
                .id(88L)
                .productId(10L)
                .warehouseId(20L)
                .onHand(10)
                .reserved(9)
                .build();

        CartItem request = CartItem.builder()
                .userId(99L)
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .quantity(1)
                .build();

        when(productGateway.findBySku("SKU-100")).thenReturn(Optional.of(product));
        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.findByUserId(99L)).thenReturn(List.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(existingItem));
        when(inventoryBalanceGateway.findByProductAndWarehouse(10L, 20L)).thenReturn(Optional.of(balance));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of(ownReservation));
        when(cartItemGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));

        CartItem result = cartItemUseCase.execute(request);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemGateway).saveItem(captor.capture());
        assertEquals(6, captor.getValue().quantity());
        assertEquals(6, result.quantity());
    }

    @Test
    void executeShouldThrowWhenRequestedQuantityExceedsAvailableStock() {
        Product product = Product.builder().id(10L).sku("SKU-100").build();
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem existingItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(1)
                .build();
        InventoryBalance balance = InventoryBalance.builder()
                .id(88L)
                .productId(10L)
                .warehouseId(20L)
                .onHand(10)
                .reserved(8)
                .build();
        CartItem request = CartItem.builder()
                .userId(99L)
                .skuProduct("SKU-100")
                .codeWarehouse("WH-01")
                .quantity(2)
                .build();

        when(productGateway.findBySku("SKU-100")).thenReturn(Optional.of(product));
        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.findByUserId(99L)).thenReturn(List.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(existingItem));
        when(inventoryBalanceGateway.findByProductAndWarehouse(10L, 20L)).thenReturn(Optional.of(balance));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of());

        BusinessException exception = assertThrows(BusinessException.class, () -> cartItemUseCase.execute(request));

        assertEquals(ErrorCode.INSUFFICIENT_STOCK, exception.getErrorCode());
    }
}
