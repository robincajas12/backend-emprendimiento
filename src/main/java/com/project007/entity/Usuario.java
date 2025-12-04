package com.project007.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    private String telefono;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    @Builder.Default
    private Date fechaCreacion = new Date();

    @OneToMany(mappedBy = "organizador")
    @ToString.Exclude
    @Builder.Default
    
    @JsonIgnore
    private List<Evento> eventosOrganizados = new ArrayList<>();

    @OneToMany(mappedBy = "asistente")
    @ToString.Exclude
    @Builder.Default

    @JsonIgnore
    private List<Entrada> entradasCompradas = new ArrayList<>();
    
    @OneToMany(mappedBy = "asistente")
    @ToString.Exclude
    @Builder.Default

    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = new Date();
        }
    }

    public enum Rol {
        ASISTENTE,
        ORGANIZADOR
    }
}
