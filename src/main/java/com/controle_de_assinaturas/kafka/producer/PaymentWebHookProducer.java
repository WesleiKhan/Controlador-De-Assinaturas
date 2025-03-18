package com.controle_de_assinaturas.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentWebHookProducer {

    private static final String TOPIC = "payment_success";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPaymentWebHook(PaymentWebHookDTO data) {

        String message = String.format("{\"subscription_id\": \"%s\", " +
                "\"event\": \"%s\", \"amount\": \"%s\", \"date\": \"%s\"}",
                data.getSubscription_id(), data.getEvent(), data.getAmount()
                , data.getDate());

        kafkaTemplate.send(TOPIC, message);
    }
}
