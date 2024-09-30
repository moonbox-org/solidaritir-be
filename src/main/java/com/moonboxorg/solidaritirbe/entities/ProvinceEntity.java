package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "provinces")
public class ProvinceEntity extends AuditableEntity {

    @Id
    @Column(name = "code", length = 2)
    private String code;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

    @Column(name = "region", nullable = false, updatable = false)
    private String region;

    @Lazy
    @OneToMany(mappedBy = "province")
    private List<CollectionPointEntity> collectionPoints;

}
