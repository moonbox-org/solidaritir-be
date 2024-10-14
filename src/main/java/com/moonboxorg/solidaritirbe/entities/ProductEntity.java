package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.EAN;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {
                        "name",
                        "category_id",
                        "container_type_id"
                })
        }
)
public class ProductEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "container_type_id")
    private ContainerTypeEntity containerType;

    @Column(name = "description")
    private String description;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "active", nullable = false)
    private boolean active;

    @EAN
    @Column(name = "ean13", length = 13, unique = true)
    private String ean13;

}
