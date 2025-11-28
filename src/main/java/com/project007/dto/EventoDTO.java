package com.project007.dto;

import com.project007.entity.Permiso;
import com.project007.entity.TipoEntrada;
import com.project007.entity.Ubicacion;

import java.util.Date;
import java.util.List;

// Usamos un Record para un DTO inmutable y conciso
public record EventoDTO(
    Long organizadorId,
    String nombre,
    String descripcion,
    Date fechaInicio,
    Date fechaFin,
    Integer capacidadAforo,
    String categoria,
    Ubicacion ubicacion,
    List<TipoEntrada> tiposDeEntrada,
    List<Permiso> permisos
) {}
