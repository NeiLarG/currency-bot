package com.neilarg.currencybot.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "regions", indexes = @Index(name = "region_idx", columnList = "id, name"))
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false, insertable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
}
