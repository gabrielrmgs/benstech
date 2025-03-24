package com.foxinline.benstech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
public class Bem implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_bem", sequenceName = "seq_bem")
    @GeneratedValue(generator = "seq_bem", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nomeProduto;
    private TipoProduto tipoProduto;
    private double precoCompra;
    private LocalDate dataCompra;
    private int vidaUtil;
    private double valorResidual;
    private boolean ativo;

    {
        this.ativo = true;
    }

    public Bem() {
    }

    public Bem(String nomeProduto, TipoProduto tipoProduto, double precoCompra, LocalDate dataCompra, int vidaUtil, double valorResidual) {
        this.nomeProduto = nomeProduto;
        this.tipoProduto = tipoProduto;
        this.precoCompra = precoCompra;
        this.dataCompra = dataCompra;
        this.vidaUtil = vidaUtil;
        this.valorResidual = valorResidual;
    }

    public double calcularDepreciacaoAnual() {
        return (this.precoCompra - this.valorResidual) / this.vidaUtil;
    }

    public String bemParaManutenção() {
        return (LocalDate.now().getYear() - this.dataCompra.getYear()) >= (this.vidaUtil * 0.75) ? "Sim" : "Não";
    }

    public String calcularTotalDepreciacaoAtual() {
//        return (LocalDate.now().getYear() - this.dataCompra.getYear()) * this.calcularDepreciacaoAnual();
        int meses = 0;
        meses += (LocalDate.now().getYear() - this.dataCompra.getYear()) * 12;
        meses += (LocalDate.now().getMonthValue() - this.dataCompra.getMonthValue());

        double calculoFinal = calcularDepreciacaoMensal() * meses;
        String valorFormatado = String.format("%.2f", calculoFinal);
        System.out.println(valorFormatado);

        return valorFormatado;
    }

    public double calcularDepreciacaoPorAno(int ano) {
        if (ano < this.dataCompra.getYear()) {
            throw new IllegalArgumentException("Ano selecionado é menor que o ano da compra!");
        }
        return (ano - this.dataCompra.getYear()) * calcularDepreciacaoAnual();
    }

    public double calcularValorAtualDoBem() {
        return this.precoCompra - Double.parseDouble(calcularTotalDepreciacaoAtual().replace(",", "."));
    }

    public double calcularDepreciacaoMensal() {
        return this.calcularDepreciacaoAnual() / 12;
    }

    public Long getId() {
        return id;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public TipoProduto getTipoProduto() {
        return tipoProduto;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public LocalDate getDataCompra() {
        return dataCompra;
    }

    public int getVidaUtil() {
        return vidaUtil;
    }

    public double getValorResidual() {
        return valorResidual;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public void setTipoProduto(TipoProduto tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public void setDataCompra(LocalDate dataCompra) {
        this.dataCompra = dataCompra;
    }

    public void setVidaUtil(int vidaUtil) {
        this.vidaUtil = vidaUtil;
    }

    public void setValorResidual(double valorResidual) {
        this.valorResidual = valorResidual;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nomeProduto);
        hash = 29 * hash + Objects.hashCode(this.tipoProduto);
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.precoCompra) ^ (Double.doubleToLongBits(this.precoCompra) >>> 32));
        hash = 29 * hash + Objects.hashCode(this.dataCompra);
        hash = 29 * hash + this.vidaUtil;
        hash = 29 * hash + (int) (Double.doubleToLongBits(this.valorResidual) ^ (Double.doubleToLongBits(this.valorResidual) >>> 32));
        hash = 29 * hash + (this.ativo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bem other = (Bem) obj;
        if (Double.doubleToLongBits(this.precoCompra) != Double.doubleToLongBits(other.precoCompra)) {
            return false;
        }
        if (this.vidaUtil != other.vidaUtil) {
            return false;
        }
        if (Double.doubleToLongBits(this.valorResidual) != Double.doubleToLongBits(other.valorResidual)) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.nomeProduto, other.nomeProduto)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.tipoProduto, other.tipoProduto)) {
            return false;
        }
        return Objects.equals(this.dataCompra, other.dataCompra);
    }

    @Override
    public String toString() {
        return "Bem{" + "id=" + id + ", nomeProduto=" + nomeProduto + ", tipoProduto=" + tipoProduto + ", precoCompra=" + precoCompra + ", dataCompra=" + dataCompra + ", vidaUtil=" + vidaUtil + ", valorResidual=" + valorResidual + ", ativo=" + ativo + '}';
    }

}
