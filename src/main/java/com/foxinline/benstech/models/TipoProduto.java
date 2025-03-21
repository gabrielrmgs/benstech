package com.foxinline.benstech.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

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

    public TipoProduto(Long id, String tipo, boolean ativo) {
        this.id = id;
        this.tipo = tipo;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return tipo;
    }

    public String valorDropdown() {
        return tipo;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.tipo);
        hash = 19 * hash + (this.ativo ? 1 : 0);
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
        final TipoProduto other = (TipoProduto) obj;
        if (this.ativo != other.ativo) {
            return false;
        }
        if (!Objects.equals(this.tipo, other.tipo)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

}
