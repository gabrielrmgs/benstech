package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.TipoProduto;
import com.foxinline.benstech.services.ServiceBem;
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
public class ManagerBem implements Serializable {

    @EJB
    private ServiceBem serviceBem;

    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    private Bem bem;
    private List<Bem> bens;
    private String idTipoSelecionado;
    private TipoProduto tipoSelecionado;

    @PostConstruct
    public void init() {
        this.bem = new Bem();
        this.bens = new ArrayList<>();
        this.bens = serviceBem.findAll();
        this.tipoSelecionado = new TipoProduto();

    }

    public void salvarBem() {
        this.tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
        this.bem.setTipoProduto(tipoSelecionado);
        serviceBem.salvar(this.bem);
        this.bem = new Bem();
        this.bens = serviceBem.findAll();
    }

    public void buscarTodosBens() {
        this.bens = serviceBem.findAll();
    }

    public void inativarBem(Bem bem) {
        bem.setAtivo(false);
        serviceBem.atualizar(bem);
        this.bens = serviceBem.findAll();

    }

    public ServiceBem getServiceBem() {
        return serviceBem;
    }

    public Bem getBem() {
        return bem;
    }

    public List<Bem> getBens() {
        return bens;
    }

    public TipoProduto getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(TipoProduto tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

    public String getIdTipoSelecionado() {
        return idTipoSelecionado;
    }

    public void setIdTipoSelecionado(String idTipoSelecionado) {
        this.idTipoSelecionado = idTipoSelecionado;
    }

}
