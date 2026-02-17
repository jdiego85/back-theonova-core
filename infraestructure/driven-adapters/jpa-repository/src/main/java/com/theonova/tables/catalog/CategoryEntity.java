package com.theonova.tables.catalog;

import com.theonova.tables.utils.concurrence.ConcurrencyEntity;
import jakarta.persistence.*;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class CategoryEntity extends ConcurrencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Comment("Parent category reference")
    private CategoryEntity parent;

    @Column(name = "name", nullable = false, length = 120)
    @Comment("Category name")
    private String name;

    @Column(name = "slug", nullable = false, length = 160)
    @Comment("Category slug")
    private String slug;


}
