package com.project007.service;

import com.project007.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PedidoService implements IPedidoService {

    @Inject
    private EntityManager em;

    @Inject
    private JwtService jwtService; //para obtener el JWT id

    @Override
    public Pedido crearPedido(Usuario asistente, List<TipoEntrada> items) {
        try {
            em.getTransaction().begin();

            BigDecimal montoTotal = items.stream()
                .map(TipoEntrada::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            Pedido pedido = Pedido.builder()
                .asistente(asistente)
                .montoTotal(montoTotal)
                .build();
                
            em.persist(pedido);

            // Simulación de una transacción de pago exitosa
            Transaccion transaccion = Transaccion.builder()
                .pedido(pedido)
                .monto(montoTotal)
                .estado(Transaccion.EstadoTransaccion.EXITOSA)
                .gatewayIdExterno(UUID.randomUUID().toString())
                .build();
            em.persist(transaccion);
            
            // Lógica para crear las entradas
            for (TipoEntrada tipo : items) {
                // Validar si hay suficientes entradas
                if ((tipo.getCantidadTotal() - tipo.getCantidadVendida()) < 1) {
                    throw new RuntimeException("No hay suficientes entradas disponibles para: " + tipo.getNombre());
                }
                // Actualizar el contador de vendidas
                tipo.setCantidadVendida(tipo.getCantidadVendida() + 1);
                em.merge(tipo);

                // Crear la entrada
                Entrada nuevaEntrada = Entrada.builder()
                    .pedido(pedido)
                    .asistente(asistente)
                    .tipoEntrada(tipo)
                    .codigoQrUnico(UUID.randomUUID().toString())
                    .estado(Entrada.EstadoEntrada.VALIDA)
                    .build();
                em.persist(nuevaEntrada);

                //forzar la sincronización con la BD para el ID
                em.flush(); 
                //generamos el JWT
                String tokenJwt = jwtService.generarTokenEntrada(nuevaEntrada);
                //actualizamos con el JWT
                nuevaEntrada.setCodigoQrUnico(tokenJwt);
                em.merge(nuevaEntrada);

                pedido.getEntradas().add(nuevaEntrada); // <<-- AÑADIDA ESTA LÍNEA PARA MANTENER LA CONSISTENCIA EN MEMORIA
            }

            em.getTransaction().commit();
            return pedido;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(em.find(Pedido.class, id));
    }

    @Override
    public void procesarCompraExitosa(Pedido pedido) {
        // La lógica principal se ha consolidado en el método crearPedido para asegurar
        // que toda la operación sea atómica desde el inicio.
        // Este método podría usarse para lógicas post-compra si fuera necesario.
    }
}
