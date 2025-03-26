package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.models.Manutencao;
import com.foxinline.benstech.models.TipoProduto;
import com.foxinline.benstech.services.ServiceBem;
import com.foxinline.benstech.services.ServiceManutencao;
import com.foxinline.benstech.services.ServiceTipoProduto;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Named
@SessionScoped
public class ManagerDetalheBem implements Serializable {

    @EJB
    private ServiceBem serviceBem;

    @EJB
    private ServiceTipoProduto serviceTipoProduto;

    @EJB
    private ServiceManutencao serviceManutencao;

    private Bem bem;
    private List<Bem> bens;
    private String idTipoSelecionado;
    private TipoProduto tipoSelecionado;
    private Bem bemSelecionado;
    private static final Locale local = new Locale("pt", "br");
    private static final NumberFormat format = NumberFormat.getCurrencyInstance(local);

    @PostConstruct
    public void init() {
        this.bem = new Bem();
        this.bens = new ArrayList<>();
        this.bens = serviceBem.findAll();
        this.tipoSelecionado = new TipoProduto();
        this.bemSelecionado = new Bem();

    }

    public String bemSelecionadoDataFormatada() {
        return this.bemSelecionado.getDataCompra().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
        return "detalheBem?faces-redirect=true";
    }

    public String manutencaoPreventiva() {

        List<Manutencao> manutencoes = new ArrayList<>();
        manutencoes = serviceManutencao.buscarManutencaoBemId(bemSelecionado.getId());

        int mesesBem = 0;
        mesesBem += (LocalDate.now().getYear() - bemSelecionado.getDataCompra().getYear()) * 12;
        mesesBem += (LocalDate.now().getMonthValue() - bemSelecionado.getDataCompra().getMonthValue());
        System.out.println(mesesBem);
        if (manutencoes.isEmpty() && mesesBem >= 12) {
            return "Sim";
        }
        for (Manutencao m : manutencoes) {
            int meses = 0;
            meses += (LocalDate.now().getYear() - m.getDataManutencao().getYear()) * 12;
            meses += (LocalDate.now().getMonthValue() - m.getDataManutencao().getMonthValue());
            System.out.println(meses);
            if (meses <= 12) {
                return "Não";
            } else {
                return "Sim";
            }
        }
        return "Não";

    }

    public String formatadorMonetarioPrecoCompra() {
        return format.format(bemSelecionado.getPrecoCompra());
    }

    public String formatadorMonetarioResidual() {
        return format.format(bemSelecionado.getValorResidual());
    }

    public String formatadorMonetarioTotalDepreciado() {
        return format.format(bemSelecionado.calcularTotalDepreciacaoAtual());
    }

    public String formatadorMonetarioDepreciacaoAnual() {
        return format.format(bemSelecionado.calcularDepreciacaoAnual());
    }

    public String formatadorMonetarioValorAtualDoBem() {
        return format.format(bemSelecionado.calcularValorAtualDoBem());
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
