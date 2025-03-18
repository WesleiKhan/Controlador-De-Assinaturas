package com.controle_de_assinaturas.subscriptions.controller;

import com.controle_de_assinaturas.subscriptions.service.SubscriptionEntryDTO;
import com.controle_de_assinaturas.subscriptions.service.SubscriptionResponse;
import com.controle_de_assinaturas.subscriptions.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {

        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/register")
    public ResponseEntity<SubscriptionResponse> registerSubscruiption(
            @RequestBody SubscriptionEntryDTO data) {

        SubscriptionResponse subscriptionResponse =
                subscriptionService.createSubscription(data);

        return ResponseEntity.ok().body(subscriptionResponse);
    }
}
