package com.theonova.tables.procurement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "suppliers")
@Getter
@Setter
@NoArgsConstructor
public class SupplierTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "name", nullable = false, length = 180)
    @Comment("Supplier name")
    private String name;

    @Column(name = "email", length = 180)
    @Comment("Supplier email")
    private String email;

    @Column(name = "phone", length = 40)
    @Comment("Supplier phone")
    private String phone;

    @Column(name = "tax_id", length = 40)
    @Comment("Tax identifier")
    private String taxId;

    @Column(name = "is_active", nullable = false)
    @Comment("Active flag")
    private boolean active;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
