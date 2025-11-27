package com.project007.db;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.List;
@ApplicationScoped
public class UserService implements IUserService{
    @Inject
    EntityManager em;
    @Override
    public void create(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    @Override
    public List<User> selectAll() {
        return em.createNativeQuery("SELECT * FROM users", User.class).getResultList();
    }
}
