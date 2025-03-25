/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.Manutencao;
import com.foxinline.benstech.services.ServiceManutencao;
import com.foxinline.benstech.utilities.Mensagem;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcio
 */
@Named
@SessionScoped
public class ManagerManutencao implements Serializable {

    @EJB
    private ServiceManutencao serviceManutencao;

    private List<Manutencao> manutencoesDoBem;
    private Manutencao manutencao;
    private Bem bemSelecionado;

    @PostConstruct
    public void init() {

        this.manutencoesDoBem = new ArrayList<>();
        this.manutencao = new Manutencao();
        this.bemSelecionado = new Bem();
    }

    public void cadastrarManutencao() {
        this.manutencao.setBem(this.bemSelecionado);
        this.serviceManutencao.salvar(this.manutencao);
        this.manutencao = new Manutencao();
        this.manutencoesDoBem = this.serviceManutencao.buscarManutencaoBemId(this.bemSelecionado.getId());
        Mensagem.messagemInfoRedirect("Cadastrado", "detalheBem.xhtml");

    }

    public void inativarManutencao(Manutencao manutencao) {
        manutencao.setAtivo(false);
        serviceManutencao.atualizar(manutencao);
        this.manutencoesDoBem = this.serviceManutencao.buscarManutencaoBemId(this.bemSelecionado.getId());
        Mensagem.messagemInfoRedirect("Cadastrado", "detalheBem.xhtml");

    }

    public ServiceManutencao getServiceManutencao() {
        return serviceManutencao;
    }

    public void setServiceManutencao(ServiceManutencao serviceManutencao) {
        this.serviceManutencao = serviceManutencao;
    }

    public List<Manutencao> getManutencoesDoBem() {
        return manutencoesDoBem;
    }

    public void setManutencoesDoBem(List<Manutencao> manutencoesDoBem) {
        this.manutencoesDoBem = manutencoesDoBem;
    }

    public Bem getBemSelecionado() {
        return bemSelecionado;
    }

    public void setBemSelecionado(Bem bemSelecionado) {
        System.out.println(bemSelecionado);
        this.bemSelecionado = bemSelecionado;
        this.manutencoesDoBem = this.serviceManutencao.buscarManutencaoBemId(this.bemSelecionado.getId());
        System.out.println(manutencoesDoBem);

    }

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

}
