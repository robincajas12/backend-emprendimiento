package com.project007.dto;

import java.util.List;

public record PedidoDTO(
    Long asistenteId,
    List<Long> tipoEntradaIds
) {}
