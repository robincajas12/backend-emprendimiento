package com.project007.controller;

import com.project007.config.CdiContainerManager;
import com.project007.dto.PedidoDTO;
import com.project007.entity.Pedido;
import com.project007.entity.TipoEntrada;
import com.project007.entity.Usuario;
import com.project007.service.IPedidoService;
import com.project007.service.IUserService;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private IPedidoService getPedidoService() {
        return CdiContainerManager.getContainer().select(IPedidoService.class).get();
    }

    private IUserService getUserService() {
        return CdiContainerManager.getContainer().select(IUserService.class).get();
    }

    // Método auxiliar para obtener TipoEntrada, ya que no creamos un servicio para él
    private TipoEntrada findTipoEntradaById(Long id) {
        EntityManager em = CdiContainerManager.getContainer().select(EntityManager.class).get();
        return em.find(TipoEntrada.class, id);
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        Usuario asistente = getUserService().findById(pedidoDTO.asistenteId())
                .orElseThrow(() -> new RuntimeException("Asistente no encontrado"));

        List<TipoEntrada> items = pedidoDTO.tipoEntradaIds().stream()
                .map(this::findTipoEntradaById)
                .collect(Collectors.toList());

        if (items.isEmpty() || items.contains(null)) {
            throw new RuntimeException("Uno o más tipos de entrada no son válidos.");
        }

        Pedido nuevoPedido = getPedidoService().crearPedido(asistente, items);
        return ResponseEntity.ok(nuevoPedido);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id) {
        return getPedidoService().findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
