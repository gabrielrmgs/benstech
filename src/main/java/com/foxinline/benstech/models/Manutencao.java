/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 *
 * @author marcio
 */
@Entity
@Table
public class Manutencao implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_manutencao", sequenceName = "seq_manutencao")
    @GeneratedValue(generator = "seq_manutencao", strategy = GenerationType.SEQUENCE)
    private Long id;

    private double custoManutencao;
    private LocalDate dataManutencao;
    private Bem bem;
    private boolean ativo;

    {
        this.ativo = true;
    }

    public Manutencao() {
    }

    public Manutencao(Long id, double custoManutencao, LocalDate dataManutencao, Bem bemManutencao) {
        this.id = id;
        this.custoManutencao = custoManutencao;
        this.dataManutencao = dataManutencao;
        this.bem = bemManutencao;
    }

    public Manutencao(double custoManutencao, LocalDate dataManutencao, Bem bemManutencao) {
        this.custoManutencao = custoManutencao;
        this.dataManutencao = dataManutencao;
        this.bem = bemManutencao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCustoManutencao() {
        return custoManutencao;
    }

    public void setCustoManutencao(double custoManutencao) {
        this.custoManutencao = custoManutencao;
    }

    public LocalDate getDataManutencao() {
        return dataManutencao;
    }
    public String getDataManutencaoFormatada() {
        return dataManutencao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    public void setDataManutencao(LocalDate dataManutencao) {
        this.dataManutencao = dataManutencao;
    }

    public Bem getBem() {
        return bem;
    }

    public void setBem(Bem bem) {
        this.bem = bem;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
        hash = 41 * hash + (int) (Double.doubleToLongBits(this.custoManutencao) ^ (Double.doubleToLongBits(this.custoManutencao) >>> 32));
        hash = 41 * hash + Objects.hashCode(this.dataManutencao);
        hash = 41 * hash + Objects.hashCode(this.bem);
        hash = 41 * hash + (this.ativo ? 1 : 0);
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
        final Manutencao other = (Manutencao) obj;
        if (Double.doubleToLongBits(this.custoManutencao) != Double.doubleToLongBits(other.custoManutencao)) {
            return false;
        }
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dataManutencao, other.dataManutencao)) {
            return false;
        }
        return Objects.equals(this.bem, other.bem);
    }

    @Override
    public String toString() {
        return "Manutencao{" + "id=" + id + ", custoManutencao=" + custoManutencao + ", dataManutencao=" + dataManutencao + ", bem=" + bem + ", ativo=" + ativo + '}';
    }

}
