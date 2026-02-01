package com.theonova.tables.checkout;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class OrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "order_number", nullable = false, length = 40, unique = true)
    @Comment("Order number")
    private String orderNumber;

    @Column(name = "user_id", nullable = false)
    @Comment("User identifier")
    private Long userId;

    @Column(name = "warehouse_id", nullable = false)
    @Comment("Warehouse identifier")
    private Long warehouseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    @Comment("Order status")
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 20)
    @Comment("Payment status")
    private PaymentStatus paymentStatus;

    @Column(name = "currency", nullable = false, length = 3)
    @Comment("Currency code")
    private String currency;

    @Column(name = "subtotal", nullable = false, precision = 12, scale = 2)
    @Comment("Subtotal amount")
    private BigDecimal subtotal;

    @Column(name = "shipping", nullable = false, precision = 12, scale = 2)
    @Comment("Shipping amount")
    private BigDecimal shipping;

    @Column(name = "total", nullable = false, precision = 12, scale = 2)
    @Comment("Total amount")
    private BigDecimal total;

    @Column(name = "shipping_name", length = 160)
    @Comment("Shipping name")
    private String shippingName;

    @Column(name = "shipping_phone", length = 40)
    @Comment("Shipping phone")
    private String shippingPhone;

    @Column(name = "shipping_address", length = 255)
    @Comment("Shipping address")
    private String shippingAddress;

    @Column(name = "notes", length = 255)
    @Comment("Order notes")
    private String notes;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
