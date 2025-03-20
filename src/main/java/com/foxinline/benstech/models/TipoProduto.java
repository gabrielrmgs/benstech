package com.foxinline.benstech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class TipoProduto implements Serializable {

    @Id
    @SequenceGenerator(name = "seq_tipo", sequenceName = "seq_tipo")
    @GeneratedValue(generator = "seq_tipo", strategy = GenerationType.SEQUENCE)
    private Long id;
    private String tipo;
    private boolean ativo;
    {
        this.ativo = true;
    }

    public TipoProduto() {
    }

    public TipoProduto(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

}
