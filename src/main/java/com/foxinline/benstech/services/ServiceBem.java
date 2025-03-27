package com.foxinline.benstech.services;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.TipoProduto;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ServiceBem extends ServiceGeneric<Bem> {

    public ServiceBem() {
        super(Bem.class);
    }

    public List<Bem> findByName(String nome) {
        return getEntityManager().createNativeQuery("select * from bem b where b.nomeProduto like '" + nome + "'").getResultList();
    }

    public List<Bem> findBemsTipoProduto(TipoProduto tipo) {
        return getEntityManager().createNativeQuery("select * from bem b where b.ativo = true and b.tipoProduto_id = " + tipo.getId()).getResultList();
    }

}
