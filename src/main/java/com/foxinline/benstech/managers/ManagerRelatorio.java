/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.services.ServiceBem;
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

    private List<Bem> bensManutencao;

    @PostConstruct
    public void init() {
        this.bensManutencao = new ArrayList<>();
        List<Bem> bensTotal = serviceBem.findAll();
        for (Bem bem : bensTotal) {
            if(bem.bemParaManutenção().equals("Sim")){
                bensManutencao.add(bem);
            }
        }
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
    
    

}
