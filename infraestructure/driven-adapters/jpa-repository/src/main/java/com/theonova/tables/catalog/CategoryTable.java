package com.theonova.tables.catalog;

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
public class CategoryTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Comment("Primary key")
    private Long id;

    @Column(name = "parent_id")
    @Comment("Parent category identifier")
    private Long parentId;

    @Column(name = "name", nullable = false, length = 120)
    @Comment("Category name")
    private String name;

    @Column(name = "slug", nullable = false, length = 160)
    @Comment("Category slug")
    private String slug;

    @Column(name = "is_active", nullable = false)
    @Comment("Active flag")
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    @Comment("Parent category reference")
    private CategoryTable parent;

    @Column(name = "created_at", nullable = false)
    @Comment("Creation timestamp")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @Comment("Last update timestamp")
    private Instant updatedAt;
}
