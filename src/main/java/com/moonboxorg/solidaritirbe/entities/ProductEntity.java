package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Length(min = 13, max = 13, message = "EAN13 code must be 13 characters long")
    @Column(name = "ean13", length = 13, unique = true)
    private String ean13;

}
