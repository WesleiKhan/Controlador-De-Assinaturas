package com.controle_de_assinaturas.kafka.consumer;

import com.controle_de_assinaturas.events.entity.Event;
import com.controle_de_assinaturas.events.entity.Type;
import com.controle_de_assinaturas.events.repositorie.EventRepository;
import com.controle_de_assinaturas.kafka.exceptions.JsonNodeErro;
import com.controle_de_assinaturas.subscriptions.entity.Status;
import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import com.controle_de_assinaturas.subscriptions.execeptions.SubscriptionNotFound;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.controle_de_assinaturas.subscriptions.repositorie.SubscriptionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumer {

    private final EventRepository eventRepository;

    private final SubscriptionRepository subscriptionRepository;

    private final ObjectMapper objectMapper;

    public EventConsumer(EventRepository eventRepository,
                         SubscriptionRepository subscriptionRepository,
                         ObjectMapper objectMapper) {

        this.eventRepository = eventRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "subscription_created", groupId = "subscription-consumer-group")
    public void consumerSubscriptionCreateEvent(String message) {

        try {
            JsonNode json = objectMapper.readTree(message);

            Event newEvent = new Event(Type.subscription_created, json, true);

            eventRepository.save(newEvent);

        } catch (Exception e) {
            throw new JsonNodeErro(e.getMessage());
        }

    }

    @KafkaListener(topics = "payment_success", groupId = "subscription" +
            "-consumer-group")
    public void consumerPaymentWebHook(String message) {

        String[] parts = message.split("\"subscription_id\":\\s*\"");

        String[] parts1 = message.split("\"event\":\\s*\"");

        if(parts.length > 1 && parts1.length > 1) {

            String subscriptionId = parts[1].split("\"")[0];

            String event = parts1[1].split("\"")[0];

            Type type = Type.UNKNOWN;

            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(SubscriptionNotFound::new);

            if(event.equals("payment_success")) {
                subscription.setStatus(Status.Ativa);
                type = Type.payment_success;

            } else if (event.equals("payment_failed")) {
                subscription.setStatus(Status.Cancelada);
                type = Type.payment_failed;

            }

            subscriptionRepository.save(subscription);

            if(type != type.UNKNOWN) {
                try {
                    JsonNode json = objectMapper.readTree(message);

                    Event newEvent = new Event(type, json, true);

                    eventRepository.save(newEvent);

                } catch (Exception e) {
                    throw new JsonNodeErro(e.getMessage());
                }
            }

        }
    }
}
