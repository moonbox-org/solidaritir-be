package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString(exclude = {"items"})
@Table(name = "packages")
public class PackageEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @OneToMany
    private List<ItemEntity> items;

    /**
     * Packages can be considered products in situations like bundles or kits.
     * Example: a hygiene kit containing a toothbrush and a toothpaste
     * (two different products combined to create a third one)
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
