package com.lao.saacplus.util;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

public class Resources {

    @PersistenceUnit
    EntityManagerFactory emf;

    @Produces @RequestScoped
    public EntityManager createEntityManager() {
        return emf.createEntityManager();
    }
}
