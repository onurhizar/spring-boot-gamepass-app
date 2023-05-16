package com.onurhizar.gamepass.model.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false)
    @Setter(AccessLevel.NONE)
    private String id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    @Setter(AccessLevel.NONE)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @LastModifiedDate
    @Setter(AccessLevel.NONE)
    private ZonedDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = ZonedDateTime.now();
        updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = ZonedDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;

        if (!Objects.equals(getClass(), o.getClass())) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;
        return this.id != null && Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
