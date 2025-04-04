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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


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
        Mensagem.messagemInfoRedirect("Manutenção cadastrada!", "detalheBem.xhtml");

    }

    public void inativarManutencao(Manutencao manutencao) {
        manutencao.setAtivo(false);
        serviceManutencao.atualizar(manutencao);
        this.manutencoesDoBem = this.serviceManutencao.buscarManutencaoBemId(this.bemSelecionado.getId());
        Mensagem.messagemInfoRedirect("Manutenção excluída!", "detalheBem.xhtml");

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
    
    public void setDataAtual() {
        manutencao.setDataManutencao(LocalDate.now());
    }

    public Manutencao getManutencao() {
        return manutencao;
    }

    public void setManutencao(Manutencao manutencao) {
        this.manutencao = manutencao;
    }

}
