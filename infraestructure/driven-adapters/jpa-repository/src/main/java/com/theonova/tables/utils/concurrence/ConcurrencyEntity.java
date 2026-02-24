package com.theonova.tables.utils.concurrence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@JsonIgnoreProperties(value = {"createdAt", "updatedAt", "version"}, allowGetters = true)
@ToString
@EntityListeners(AuditingEntityListener.class)
public abstract class ConcurrencyEntity implements Serializable {

    @Comment("Version number used for optimistic locking concurrency control")
    @Version
    @Column(nullable = false)
    @Getter @Setter
    private Long version;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    @Comment("Logical deletion flag (true = active, false = deleted)")
    @Getter @Setter
    private Boolean active = true;

    @Comment("Record creation timestamp")
    @Column(name = "created_at")
    @CreatedDate
    @Getter @Setter
    private Instant createdAt;

    @Comment("Record last update timestamp")
    @Column(name = "updated_at")
    @LastModifiedDate
    @Getter @Setter
    private Instant updatedAt;

    @Comment("User who created the record")
    @Column(name = "created_by", length = 100, nullable = false, updatable = false)
    @Getter @Setter
    @CreatedBy
    private String createdBy;

    @Comment("User who last updated the record")
    @Column(name = "updated_by", length = 100)
    @Getter @Setter
    @LastModifiedBy
    private String updatedBy;

    @PrePersist
    private void setCreatedByUser() {
        //TODO get the authenticated user
    }
}
