package com.theonova.business.checkout;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.checkout.Order;
import com.theonova.entities.checkout.OrderCheckout;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.OrderStatus;
import com.theonova.enums.PaymentStatus;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.checkout.OrderGateway;
import com.theonova.gateways.checkout.OrderItemGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import com.theonova.service.checkout.OrderCheckoutService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderCheckoutUseCaseTest {

    @Mock
    private WarehouseGateway warehouseGateway;
    @Mock
    private ProductGateway productGateway;
    @Mock
    private CartGateway cartGateway;
    @Mock
    private CartItemGateway cartItemGateway;
    @Mock
    private OrderGateway orderGateway;
    @Mock
    private OrderItemGateway orderItemGateway;
    @Mock
    private StockReservationGateway stockReservationGateway;

    @Spy
    private OrderCheckoutService orderCheckoutService = new OrderCheckoutService();

    @InjectMocks
    private OrderCheckoutUseCase orderCheckoutUseCase;

    @Test
    void executeShouldCreateOrderAndCheckoutCart() {
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(2)
                .build();
        StockReservation reservation = StockReservation.builder()
                .id(55L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(2)
                .status(ReservationStatus.ACTIVE)
                .build();
        Product product = Product.builder()
                .id(10L)
                .sku("SKU-100")
                .name("Producto 100")
                .price(new BigDecimal("12.50"))
                .build();

        Order createdOrder = new Order(
                101L, "EC-20260307-00001", 99L, 20L, OrderStatus.PENDING, PaymentStatus.PENDING,
                "USD", BigDecimal.ZERO, new BigDecimal("5.00"), new BigDecimal("5.00"),
                "Cliente", "099", "Quito", "Nota", Instant.parse("2026-03-07T12:00:00Z"), Instant.now()
        );
        Order finalizedOrder = new Order(
                101L, "EC-20260307-00001", 99L, 20L, OrderStatus.PENDING, PaymentStatus.PENDING,
                "USD", new BigDecimal("25.00"), new BigDecimal("5.00"), new BigDecimal("30.00"),
                "Cliente", "099", "Quito", "Nota", Instant.parse("2026-03-07T12:00:00Z"), Instant.now()
        );

        OrderCheckout request = OrderCheckout.builder()
                .userId(99L)
                .codeWarehouse("WH-01")
                .shippingName("Cliente")
                .shippingPhone("099")
                .shippingAddress("Quito")
                .currency("usd")
                .shipping(new BigDecimal("5"))
                .notes("Nota")
                .build();

        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.lockActiveByUserId(99L)).thenReturn(Optional.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(cartItem));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of(reservation));
        when(orderGateway.findByOrderNumber(anyString())).thenReturn(Optional.empty());
        when(orderGateway.saveItem(any())).thenReturn(createdOrder, finalizedOrder);
        when(productGateway.findById(10L)).thenReturn(Optional.of(product));
        when(orderItemGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(stockReservationGateway.saveItem(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var response = orderCheckoutUseCase.execute(request);

        assertEquals(101L, response.orderId());
        assertEquals("PENDING", response.status());
        assertEquals(new BigDecimal("25.00"), response.subtotal());
        assertEquals(new BigDecimal("5.00"), response.shipping());
        assertEquals(new BigDecimal("30.00"), response.total());
        assertEquals(1, response.items().size());
        assertEquals("SKU-100", response.items().getFirst().skuProduct());

        ArgumentCaptor<StockReservation> reservationCaptor = ArgumentCaptor.forClass(StockReservation.class);
        verify(stockReservationGateway).saveItem(reservationCaptor.capture());
        assertEquals(ReservationStatus.CONSUMED, reservationCaptor.getValue().status());
        assertEquals(101L, reservationCaptor.getValue().orderId());
    }

    @Test
    void executeShouldFailWhenNoActiveReservationCoversCartItems() {
        Warehouse warehouse = Warehouse.builder().id(20L).code("WH-01").build();
        Cart activeCart = new Cart(30L, 99L, CartStatus.ACTIVE, Instant.now(), null);
        CartItem cartItem = CartItem.builder()
                .id(1L)
                .cartId(30L)
                .productId(10L)
                .warehouseId(20L)
                .quantity(2)
                .build();

        OrderCheckout request = OrderCheckout.builder()
                .userId(99L)
                .codeWarehouse("WH-01")
                .shippingName("Cliente")
                .shippingPhone("099")
                .shippingAddress("Quito")
                .currency("USD")
                .build();

        when(warehouseGateway.findByCode("WH-01")).thenReturn(Optional.of(warehouse));
        when(cartGateway.lockActiveByUserId(99L)).thenReturn(Optional.of(activeCart));
        when(cartItemGateway.findByCartId(30L)).thenReturn(List.of(cartItem));
        when(stockReservationGateway.findByCartId(30L)).thenReturn(List.of());

        BusinessException exception = assertThrows(BusinessException.class, () -> orderCheckoutUseCase.execute(request));
        assertEquals(ErrorCode.INSUFFICIENT_STOCK, exception.getErrorCode());
    }
}
