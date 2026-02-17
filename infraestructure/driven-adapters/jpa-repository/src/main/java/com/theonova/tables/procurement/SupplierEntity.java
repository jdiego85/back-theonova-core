package com.theonova.tables.procurement;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
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
public class SupplierEntity extends ConcurrencyEntity {
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

}
