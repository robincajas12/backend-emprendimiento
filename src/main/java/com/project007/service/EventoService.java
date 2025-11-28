package com.project007.service;

import com.project007.entity.Evento;
import com.project007.entity.Permiso;
import com.project007.entity.TipoEntrada;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class EventoService implements IEventoService {

    @Inject
    private EntityManager em;

    @Override
    public Evento crear(Evento evento) {
        try {
            em.getTransaction().begin();

            // Persistir la Ubicacion primero si es nueva
            if (evento.getUbicacion() != null && evento.getUbicacion().getId() == null) {
                em.persist(evento.getUbicacion());
            }

            // Persistir la entidad Evento principal
            em.persist(evento);

            // Asignar el evento a sus hijos y persistirlos manualmente
            if (evento.getTiposDeEntrada() != null) {
                for (TipoEntrada tipo : evento.getTiposDeEntrada()) {
                    tipo.setEvento(evento);
                    em.persist(tipo);
                }
            }
            if (evento.getPermisos() != null) {
                for (Permiso permiso : evento.getPermisos()) {
                    permiso.setEvento(evento);
                    em.persist(permiso);
                }
            }
            
            em.getTransaction().commit();
            return evento;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Evento> findById(Long id) {
        return Optional.ofNullable(em.find(Evento.class, id));
    }

    @Override
    public List<Evento> findAll() {
        return em.createQuery("SELECT e FROM Evento e", Evento.class).getResultList();
    }

    @Override
    public Evento actualizar(Evento evento) {
        try {
            em.getTransaction().begin();
            Evento updatedEvento = em.merge(evento);
            em.getTransaction().commit();
            return updatedEvento;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            em.getTransaction().begin();
            findById(id).ifPresent(em::remove);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
