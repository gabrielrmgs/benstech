package com.foxinline.benstech.services;

import com.foxinline.benstech.models.TipoProduto;
import jakarta.ejb.Stateless;
import java.util.List;

@Stateless
public class ServiceTipoProduto extends ServiceGeneric<TipoProduto> {

    public ServiceTipoProduto() {
        super(TipoProduto.class);
    }

    public List<TipoProduto> findByName(String nome) {
        return getEntityManager().createNativeQuery("select * from tipoproduto t where t.tipo like '"+nome+"'").getResultList();
    }
    
}
