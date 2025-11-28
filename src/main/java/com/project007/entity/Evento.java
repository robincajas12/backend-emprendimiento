package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "organizador_id", nullable = false)
    private Usuario organizador;

    @Column(nullable = false)
    private String nombre;

    @Lob
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id", referencedColumnName = "id")
    private Ubicacion ubicacion;

    @Column(name = "capacidad_aforo")
    private Integer capacidadAforo;

    private String categoria;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "es_destacado", nullable = false)
    @Builder.Default
    private Boolean esDestacado = false;
    
    @OneToMany(mappedBy = "evento")
    @ToString.Exclude
    @Builder.Default
    private List<TipoEntrada> tiposDeEntrada = new ArrayList<>();

    @OneToMany(mappedBy = "evento")
    @ToString.Exclude
    @Builder.Default
    private List<Permiso> permisos = new ArrayList<>();

    public enum EstadoEvento {
        BORRADOR,
        PUBLICADO,
        ACTIVO,
        FINALIZADO,
        CANCELADO
    }
}
