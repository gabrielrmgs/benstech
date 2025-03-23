package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.TipoProduto;
import com.foxinline.benstech.services.ServiceTipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class ManagerTipoProduto implements Serializable {

    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    private TipoProduto tipoProduto;
    private List<TipoProduto> tipos;

    private TipoProduto tipoSelecionado;

    @PostConstruct
    public void init() {
        this.tipoProduto = new TipoProduto();
        this.tipos = new ArrayList<>();
        this.tipos = serviceTipoProduto.findAll();
    }

    public void salvarTipo() {
        serviceTipoProduto.salvar(this.tipoProduto);
        this.tipoProduto = new TipoProduto();
        this.tipos = serviceTipoProduto.findAll();
    }

    public void inativarTipo(TipoProduto tipo) {
        tipo.setAtivo(false);
        serviceTipoProduto.atualizar(tipo);
        this.tipos = serviceTipoProduto.findAll();
    }

    public void atualizarTipo() {
        serviceTipoProduto.atualizar(tipoSelecionado);
    }

    public List<TipoProduto> buscarTodosTipos() {
        return serviceTipoProduto.findAll();
    }

    public ServiceTipoProduto getServiceTipoProduto() {
        return serviceTipoProduto;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }

    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
        this.serviceTipoProduto = serviceTipoProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public void setTipos(List<TipoProduto> tipos) {
        this.tipos = tipos;
    }

    public TipoProduto getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(TipoProduto tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

}
