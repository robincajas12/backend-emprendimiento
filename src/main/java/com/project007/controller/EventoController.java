package com.project007.controller;

import com.project007.config.CdiContainerManager;
import com.project007.dto.EventoDTO;
import com.project007.entity.Evento;
import com.project007.entity.Usuario;
import com.project007.service.IEventoService;
import com.project007.service.IUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    private IEventoService getEventoService() {
        return CdiContainerManager.getContainer().select(IEventoService.class).get();
    }
    
    private IUserService getUserService() {
        return CdiContainerManager.getContainer().select(IUserService.class).get();
    }

    @PostMapping
    public ResponseEntity<Evento> crearEvento(@RequestBody EventoDTO eventoDTO) {
        // En una app real, aquí habría validaciones y mapeo más robusto
        Usuario organizador = getUserService().findById(eventoDTO.organizadorId())
                .orElseThrow(() -> new RuntimeException("Organizador no encontrado"));

        Evento nuevoEvento = Evento.builder()
                .organizador(organizador)
                .nombre(eventoDTO.nombre())
                .descripcion(eventoDTO.descripcion())
                .fechaInicio(eventoDTO.fechaInicio())
                .fechaFin(eventoDTO.fechaFin())
                .capacidadAforo(eventoDTO.capacidadAforo())
                .categoria(eventoDTO.categoria())
                .estado(Evento.EstadoEvento.PUBLICADO) // O 'BORRADOR' por defecto
                .ubicacion(eventoDTO.ubicacion())
                .tiposDeEntrada(eventoDTO.tiposDeEntrada())
                .permisos(eventoDTO.permisos())
                .build();
        
        Evento eventoCreado = getEventoService().crear(nuevoEvento);
        return ResponseEntity.ok(eventoCreado);
    }

    @GetMapping
    public ResponseEntity<List<Evento>> obtenerTodos() {
        List<Evento> eventos = getEventoService().findAll();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> obtenerPorId(@PathVariable Long id) {
        return getEventoService().findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        getEventoService().eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
