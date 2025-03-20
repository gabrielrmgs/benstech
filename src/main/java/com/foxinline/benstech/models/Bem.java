package com.foxinline.benstech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

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

}
