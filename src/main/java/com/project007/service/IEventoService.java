package com.project007.service;

import com.project007.entity.Evento;
import java.util.List;
import java.util.Optional;

public interface IEventoService {
    
    Evento crear(Evento evento);

    Optional<Evento> findById(Long id);

    List<Evento> findAll();

    Evento actualizar(Evento evento);
    
    void eliminar(Long id);
}
