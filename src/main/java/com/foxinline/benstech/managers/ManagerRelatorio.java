/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcio
 */
@Named
@ViewScoped
public class ManagerRelatorio implements Serializable {

    @EJB
    private ServiceBem serviceBem;
    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    private List<Bem> bensManutencao;
    private List<Bem> bensManutencaoFiltro;
    private List<TipoProduto> tipos;

    private String idTipoSelecionado;

    @PostConstruct
    public void init() {
        this.bensManutencao = new ArrayList<>();
        this.bensManutencaoFiltro = new ArrayList<>();
        this.tipos = new ArrayList<>();
        tipos = serviceTipoProduto.findAll();
        List<Bem> bensTotal = serviceBem.findAll();
        for (Bem bem : bensTotal) {
            if (bem.bemParaManutenção().equals("Sim")) {
                bensManutencao.add(bem);
            }
        }
        bensManutencaoFiltro.addAll(bensManutencao);
    }

    public void filtrarPorTipo() {

        if (idTipoSelecionado == "" || idTipoSelecionado == null) {
            bensManutencaoFiltro.clear();
            bensManutencaoFiltro.addAll(bensManutencao);
            return;
        }

        bensManutencaoFiltro.clear();

        TipoProduto tipoSelecionado = new TipoProduto();
        tipoSelecionado = serviceTipoProduto.findById(Long.valueOf(idTipoSelecionado));
        System.out.println(bensManutencao);
        System.out.println(tipoSelecionado);
        for (Bem bem : bensManutencao) {
            if (bem.getTipoProduto().equals(tipoSelecionado)) {
                bensManutencaoFiltro.add(bem);
            }
        }
        System.out.println(bensManutencaoFiltro);

    }

    public ServiceBem getServiceBem() {
        return serviceBem;
    }

    public void setServiceBem(ServiceBem serviceBem) {
        this.serviceBem = serviceBem;
    }

    public List<Bem> getBensManutencao() {
        return bensManutencao;
    }

    public void setBensManutencao(List<Bem> bensManutencao) {
        this.bensManutencao = bensManutencao;
    }

    public String getIdTipoSelecionado() {
        return idTipoSelecionado;
    }

    public void setIdTipoSelecionado(String idTipoSelecionado) {
        this.idTipoSelecionado = idTipoSelecionado;
    }

    public List<Bem> getBensManutencaoFiltro() {
        return bensManutencaoFiltro;
    }

    public void setBensManutencaoFiltro(List<Bem> bensManutencaoFiltro) {
        this.bensManutencaoFiltro = bensManutencaoFiltro;
    }

    public ServiceTipoProduto getServiceTipoProduto() {
        return serviceTipoProduto;
    }

    public void setServiceTipoProduto(ServiceTipoProduto serviceTipoProduto) {
        this.serviceTipoProduto = serviceTipoProduto;
    }

    public List<TipoProduto> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoProduto> tipos) {
        this.tipos = tipos;
    }

}
