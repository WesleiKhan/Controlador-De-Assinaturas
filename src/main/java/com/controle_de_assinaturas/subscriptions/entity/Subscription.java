package com.controle_de_assinaturas.subscriptions.entity;

import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.subscriptions.service.SubscriptionEntryDTO;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    private Plan plan;

    @Column(name = "customer_email")
    private String customerEmail;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status = Status.Pendente;

    @Column
    private LocalDate nextBillingDate = LocalDate.now().plusMonths(1);

    public Subscription() {
    }

    public Subscription(Plan plan, String customerEmail) {
        this.plan = plan;
        this.customerEmail = customerEmail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Plan getPlan() {
        return plan;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getNextBillingDate() {
        return nextBillingDate;
    }
}
