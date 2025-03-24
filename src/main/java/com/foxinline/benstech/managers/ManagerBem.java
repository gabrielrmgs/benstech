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
    private Bem bemSelecionado;


    @PostConstruct
    public void init() {
        this.bem = new Bem();
        this.bens = new ArrayList<>();
        this.bens = serviceBem.findAll();
        this.tipoSelecionado = new TipoProduto();
        this.bemSelecionado = new Bem();

    }

    public void salvarBem() {
        this.tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
        this.bem.setTipoProduto(tipoSelecionado);
        serviceBem.salvar(this.bem);
        this.bens = serviceBem.findAll();
        this.bem = new Bem();
        this.tipoSelecionado = new TipoProduto();
    }

    public void atualizarBem() {
        this.tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
        this.bemSelecionado.setTipoProduto(tipoSelecionado);
        serviceBem.atualizar(this.bemSelecionado);
    }

    public void buscarTodosBens() {
        this.bens = serviceBem.findAll();
    }

    public void inativarBem(Bem bem) {
        bem.setAtivo(false);
        serviceBem.atualizar(bem);
        this.bens = serviceBem.findAll();

    }

    public String encaminharDetalhes(Bem bem) {
        this.bemSelecionado = bem;
        return "detalheBem";
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

    public ServiceTipoProduto getServiceTipoProduto() {
        return serviceTipoProduto;
    }

    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
        this.serviceTipoProduto = serviceTipoProduto;
    }

    public Bem getBemSelecionado() {
        return bemSelecionado;
    }

    

    public void setBemSelecionado(Bem bemSelecionado) {
        this.idTipoSelecionado = String.valueOf(bemSelecionado.getTipoProduto().getId());
        this.bemSelecionado = bemSelecionado;
    }

}
