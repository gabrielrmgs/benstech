/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.utilities;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author marcio
 */
@Named
@ApplicationScoped
public class Formatador implements Serializable{

    private Locale br = new Locale("pt", "br");
    private NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(br);

    public Formatador() {
    }

    public Locale getBr() {
        return br;
    }

    public void setBr(Locale br) {
        this.br = br;
    }

    public NumberFormat getFormatadorMoeda() {
        return formatadorMoeda;
    }

    public void setFormatadorMoeda(NumberFormat formatadorMoeda) {
        this.formatadorMoeda = formatadorMoeda;
    }

}
