package com.foxinline.benstech.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

public class ServiceGeneric<T> {

    private Class<T> entidade;

    @PersistenceContext
    private EntityManager entityManager;

    public ServiceGeneric(Class<T> entidade) {
        this.entidade = entidade;
    }

    public void salvar(T entidade) {
        entityManager.persist(entidade);
    }

    public void atualizar(T entidade) {
        entityManager.merge(entidade);
        entityManager.flush();
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entidade.getName() + " e WHERE e.ativo = true").getResultList();
    }

    public Class<T> getEntidade() {
        return entidade;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

}