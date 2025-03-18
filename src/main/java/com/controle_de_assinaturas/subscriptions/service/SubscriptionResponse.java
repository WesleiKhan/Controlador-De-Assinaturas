package com.controle_de_assinaturas.subscriptions.service;

import com.controle_de_assinaturas.subscriptions.entity.Status;

import java.time.LocalDate;

public record SubscriptionResponse(String subscription_id,
                                   Status status,
                                   LocalDate next_billing_date) {
}
