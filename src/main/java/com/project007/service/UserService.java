package com.project007.service;

import com.project007.entity.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class UserService implements IUserService {

    @Inject
    EntityManager em;

    @Override
    public Usuario crear(Usuario usuario) {
        try {
            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
            return usuario;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return Optional.ofNullable(em.find(Usuario.class, id));
    }

    @Override
    public List<Usuario> selectAll() {
        // Se corrige la consulta a JPQL que es más segura y portable
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Override
    public Usuario actualizar(Usuario usuario) {
        try {
            em.getTransaction().begin();
            Usuario updatedUsuario = em.merge(usuario);
            em.getTransaction().commit();
            return updatedUsuario;
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

