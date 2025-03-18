package com.controle_de_assinaturas.plans.service.DTOs;

import com.controle_de_assinaturas.plans.entity.BillingCycle;

import java.math.BigDecimal;

public class PlanEntryDTO {

    private String name;

    private BigDecimal price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
