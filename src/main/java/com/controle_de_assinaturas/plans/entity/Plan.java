package com.controle_de_assinaturas.plans.entity;

import com.controle_de_assinaturas.plans.service.DTOs.PlanEntryDTO;
import com.controle_de_assinaturas.subscriptions.entity.Status;
import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "plans")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "billing_cycle")
    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle = BillingCycle.Mensal;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Subscription> subscriptions;

    public Plan() {
    }

    public Plan(PlanEntryDTO plan) {
        this.name = plan.getName();
        this.price = plan.getPrice();

    }

    public String getId() {
        return id;
    }

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

    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    public void setBillingCycle(BillingCycle billingCycle) {
        this.billingCycle = billingCycle;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public StatusSubscriptionsPlan getSubscriptions() {

        int assinaturasAtivas = 0;
        int assinaturascanceladas = 0;

        for(int i = 0; i < subscriptions.size(); i++) {

            if(subscriptions.get(i).getStatus().equals(Status.Ativa)) {

                assinaturasAtivas += 1;
            } else if(subscriptions.get(i).getStatus().equals(Status.Cancelada)) {

                assinaturascanceladas += 1;
            }
        }
        return new StatusSubscriptionsPlan(assinaturasAtivas, assinaturascanceladas);
    }
}
