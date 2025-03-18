package com.controle_de_assinaturas.subscriptions.service;

public class SubscriptionEntryDTO {

    private String planId;

    private String customerEmail;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}
