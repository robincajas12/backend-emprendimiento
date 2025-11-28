package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "ubicaciones")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_lugar")
    private String nombreLugar;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitud;

    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitud;
}
