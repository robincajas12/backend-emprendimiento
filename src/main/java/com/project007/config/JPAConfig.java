package com.project007.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@ApplicationScoped
public class JPAConfig {
    @Produces
    @Singleton
    EntityManagerFactory emf()
    {
        return Persistence.createEntityManagerFactory("default");
    }
    @Produces
    @ApplicationScoped
    EntityManager entityManager(EntityManagerFactory emf)
    {
        return emf.createEntityManager();
    }
}
