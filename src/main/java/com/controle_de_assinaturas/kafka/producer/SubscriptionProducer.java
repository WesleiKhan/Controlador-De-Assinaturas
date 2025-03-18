package com.controle_de_assinaturas.kafka.producer;

import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionProducer {

    private static final String TOPIC = "subscription_created";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendSubscriptionCreateEvent(Subscription subscription) {

        String message = String.format("{\"subscription_id\": \"%s\", " +
                "\"customer_email\": \"%s\", \"plan_id\": \"%s\", " +
                "\"status\": \"%s\", \"next_belling_date\": \"%s\"}",
                subscription.getId(), subscription.getCustomerEmail(),
                subscription.getPlan(), subscription.getStatus(),
                subscription.getNextBillingDate());

        kafkaTemplate.send(TOPIC, message);
    }
}
