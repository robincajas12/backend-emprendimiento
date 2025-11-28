package com.project007.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "permisos")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    @JsonIgnore // Ignorar durante la serialización para evitar recursión infinita
    private Evento evento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_permiso", nullable = false)
    private TipoPermiso tipoPermiso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPermiso estado;

    @Column(name = "url_documento")
    private String urlDocumento;
    
    @Lob
    private String notas;

    public enum TipoPermiso {
        MUNICIPAL,
        BOMBEROS,
        SEGURIDAD_PRIVADA
    }

    public enum EstadoPermiso {
        NO_REQUERIDO,
        PENDIENTE,
        SOLICITADO,
        APROBADO,
        RECHAZADO
    }
}
