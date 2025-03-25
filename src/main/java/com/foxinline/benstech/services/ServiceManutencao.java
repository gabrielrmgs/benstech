/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.services;

import com.foxinline.benstech.models.Manutencao;
import jakarta.ejb.Stateless;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author marcio
 */
@Stateless
public class ServiceManutencao extends ServiceGeneric<Manutencao> {

    public ServiceManutencao() {
        super(Manutencao.class);
    }

    public List<Manutencao> buscarManutencaoBemId(Long id) {
//        Query query = getEntityManager().createQuery("SELECT m FROM Manutencao m WHERE m.BemId = :idBem");
//        query.setParameter("idBem", id);
//        return query.getResultList();
        return getEntityManager().createQuery("SELECT m FROM Manutencao m WHERE m.ativo = true AND m.bem.id = " + id+" ORDER BY m.dataManutencao DESC").getResultList();
    }

}
