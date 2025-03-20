package com.foxinline.benstech.services;

import com.foxinline.benstech.models.Bem;
import jakarta.ejb.Stateless;

@Stateless
public class ServiceBem extends ServiceGeneric<Bem> {

    public ServiceBem() {
        super(Bem.class);
    }

}
