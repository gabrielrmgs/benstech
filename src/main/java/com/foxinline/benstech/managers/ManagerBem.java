package com.foxinline.benstech.managers;

import com.foxinline.benstech.models.Bem;
import com.foxinline.benstech.services.ServiceBem;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ManagerBem implements Serializable {
    
    @EJB
    private ServiceBem serviceBem;
    
    private Bem bem;
    
    @PostConstruct
    public void init() {
        this.bem = new Bem();
    }
    
    public void salvarBem() {
        serviceBem.salvar(bem);
    }

    public ServiceBem getServiceBem() {
        return serviceBem;
    }

    public Bem getBem() {
        return bem;
    }
    
    
    
}
