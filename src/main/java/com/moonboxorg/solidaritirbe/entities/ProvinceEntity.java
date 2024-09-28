package com.moonboxorg.solidaritirbe.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
