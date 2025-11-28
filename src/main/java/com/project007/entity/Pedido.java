package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedidos")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asistente_id", nullable = false)
    private Usuario asistente;

    @Column(name = "monto_total", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoTotal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Builder.Default
    private Date fechaCreacion = new Date();

    @OneToMany(mappedBy = "pedido")
    @ToString.Exclude
    @Builder.Default
    private List<Entrada> entradas = new ArrayList<>();

    @OneToMany(mappedBy = "pedido")
    @ToString.Exclude
    @Builder.Default
    private List<Transaccion> transacciones = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = new Date();
        }
    }
}
