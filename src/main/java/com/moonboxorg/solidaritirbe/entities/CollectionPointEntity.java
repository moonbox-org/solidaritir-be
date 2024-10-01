package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@Table(name = "collection_points")
public class CollectionPointEntity extends AuditableEntity {

    @Id
    @Column(name = "code")
    @GeneratedValue(strategy = IDENTITY)
    private Long code;

    @Column(name = "name")
    private String name;

    @Lazy
    @ManyToOne
    @JoinColumn(name = "province_id")
    private ProvinceEntity province;

    @Column(name = "active")
    private boolean active;

    @Column(name = "notes")
    private String notes;

}
