package com.controle_de_assinaturas.kafka.producer;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentWebHookDTO {

    private String subscription_id;

    private String event;

    private BigDecimal amount;

    private LocalDate date;

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
