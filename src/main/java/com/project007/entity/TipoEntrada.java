package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
//para el ejemplo
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tipos_entrada")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore // Ignorar durante la serialización para evitar recursión infinita
    private Evento evento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "cantidad_total", nullable = false)
    private Integer cantidadTotal;

    @Column(name = "cantidad_vendida", nullable = false)
    private int cantidadVendida = 0;

    @OneToMany(mappedBy = "tipoEntrada")
    @ToString.Exclude
    @Builder.Default
    @JsonIgnore
    private List<Entrada> entradas = new ArrayList<>();
}
