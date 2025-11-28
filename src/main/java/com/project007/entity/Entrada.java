package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "entradas")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tipo_entrada_id", nullable = false)
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JoinColumn(name = "asistente_id", nullable = false)
    private Usuario asistente;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @Column(name = "codigo_qr_unico", nullable = false, unique = true)
    private String codigoQrUnico;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEntrada estado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_compra", nullable = false, updatable = false)
    @Builder.Default
    private Date fechaCompra = new Date();

    @PrePersist
    protected void onCreate() {
        if (fechaCompra == null) {
            fechaCompra = new Date();
        }
    }

    public enum EstadoEntrada {
        VALIDA,
        UTILIZADA,
        REEMBOLSADA
    }
}
