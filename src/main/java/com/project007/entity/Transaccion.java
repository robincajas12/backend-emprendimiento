package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transacciones")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "gateway_id_externo")
    private String gatewayIdExterno;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTransaccion estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_procesamiento", nullable = false, updatable = false)
    @Builder.Default
    private Date fechaProcesamiento = new Date();

    @PrePersist
    protected void onCreate() {
        if (fechaProcesamiento == null) {
            fechaProcesamiento = new Date();
        }
    }

    public enum EstadoTransaccion {
        PENDIENTE,
        EXITOSA,
        FALLIDA,
        REEMBOLSADA
    }
}
