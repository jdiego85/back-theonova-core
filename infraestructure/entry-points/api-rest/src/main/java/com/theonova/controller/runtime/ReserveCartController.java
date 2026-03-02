package com.theonova.controller.runtime;

import com.theonova.entities.catalog.Product;
import com.theonova.entities.catalog.Warehouse;
import com.theonova.entities.checkout.Cart;
import com.theonova.entities.checkout.CartItem;
import com.theonova.entities.inventory.InventoryBalance;
import com.theonova.entities.inventory.InventoryMovement;
import com.theonova.entities.inventory.StockReservation;
import com.theonova.enums.CartStatus;
import com.theonova.enums.ErrorCode;
import com.theonova.enums.InventoryMovementType;
import com.theonova.enums.InventoryReferenceType;
import com.theonova.enums.ReservationStatus;
import com.theonova.exceptions.BusinessException;
import com.theonova.gateways.catalog.ProductGateway;
import com.theonova.gateways.catalog.WarehouseGateway;
import com.theonova.gateways.checkout.CartGateway;
import com.theonova.gateways.checkout.CartItemGateway;
import com.theonova.gateways.inventory.InventoryBalanceGateway;
import com.theonova.gateways.inventory.InventoryMovementGateway;
import com.theonova.gateways.inventory.StockReservationGateway;
import com.theonova.request.runtime.ReserveCartRequest;
import com.theonova.response.ApiResponseWrapper;
import com.theonova.response.runtime.ReservedItemResponse;
import com.theonova.response.runtime.ReserveCartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reservation/")
@RequiredArgsConstructor
public class ReserveCartController {

    private static final ZoneId GUAYAQUIL_ZONE = ZoneId.of("America/Guayaquil");
    private final ProductGateway productGateway;
    private final WarehouseGateway warehouseGateway;
    private final CartGateway cartGateway;
    private final CartItemGateway cartItemGateway;
    private final InventoryBalanceGateway inventoryBalanceGateway;
    private final StockReservationGateway stockReservationGateway;
    private final InventoryMovementGateway inventoryMovementGateway;

    @PostMapping("create")
    @Transactional
    public ResponseEntity<ApiResponseWrapper<ReserveCartResponse>> create(@Valid @RequestBody ReserveCartRequest request) {
        Product product = productGateway.findBySku(request.getSkuProduct())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_PRODUCT_ID));
        Warehouse warehouse = warehouseGateway.findByCode(request.getCodeWarehouse())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_WAREHOUSE_CODE));

        Cart activeCart = cartGateway.findByUserId(request.getUserId()).stream()
                .filter(cart -> CartStatus.ACTIVE.equals(cart.status()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        CartItem cartItem = cartItemGateway.findByCartId(activeCart.id()).stream()
                .filter(item -> Objects.equals(item.productId(), product.id())
                        && Objects.equals(item.warehouseId(), warehouse.id()))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR));

        InventoryBalance lockedBalance = inventoryBalanceGateway.lockByProductAndWarehouse(product.id(), warehouse.id())
                .orElseThrow(() -> new BusinessException(ErrorCode.INSUFFICIENT_STOCK));

        int reservationQuantity = cartItem.quantity();
        int available = lockedBalance.onHand() - lockedBalance.reserved();
        if (available < reservationQuantity) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK);
        }

        inventoryBalanceGateway.saveItem(lockedBalance.toBuilder()
                .reserved(lockedBalance.reserved() + reservationQuantity)
                .build());

        Optional<StockReservation> existingReservation = stockReservationGateway.findByCartId(activeCart.id()).stream()
                .filter(reservation -> Objects.equals(reservation.productId(), product.id())
                        && Objects.equals(reservation.warehouseId(), warehouse.id())
                        && ReservationStatus.ACTIVE.equals(reservation.status()))
                .findFirst();

        stockReservationGateway.saveItem(StockReservation.builder()
                .id(existingReservation.map(StockReservation::id).orElse(null))
                .productId(product.id())
                .warehouseId(warehouse.id())
                .cartId(activeCart.id())
                .orderId(null)
                .quantity(reservationQuantity)
                .status(ReservationStatus.ACTIVE)
                .build());

        inventoryMovementGateway.saveItem(InventoryMovement.builder()
                .productId(product.id())
                .warehouseId(warehouse.id())
                .movementType(InventoryMovementType.RESERVE)
                .quantity(reservationQuantity)
                .refType(InventoryReferenceType.CART)
                .refId(activeCart.id())
                .note("Stock reserved from ReserveCartController")
                .build());

        ReserveCartResponse response = ReserveCartResponse.builder()
                .reservationStatus("RESERVED")
                .reservedItems(java.util.List.of(
                        ReservedItemResponse.builder()
                                .skuProduct(product.sku())
                                .codeWarehouse(warehouse.code())
                                .reservedQuantity(reservationQuantity)
                                .build()
                ))
                .expiresAt(LocalDateTime.now(GUAYAQUIL_ZONE).plusMinutes(30))
                .build();

        ApiResponseWrapper<ReserveCartResponse> responseWrapper = ApiResponseWrapper
                .wrapUp("serviceReserveCart", response);
        return ResponseEntity.ok(responseWrapper);
    }
}
