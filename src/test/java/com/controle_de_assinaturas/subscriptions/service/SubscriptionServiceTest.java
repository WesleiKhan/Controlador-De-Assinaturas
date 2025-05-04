package com.controle_de_assinaturas.subscriptions.service;

import com.controle_de_assinaturas.kafka.producer.SubscriptionProducer;
import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.plans.exceptions.PlanNotFound;
import com.controle_de_assinaturas.plans.repositorie.PlanRepository;
import com.controle_de_assinaturas.subscriptions.entity.Status;
import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import com.controle_de_assinaturas.subscriptions.repositorie.SubscriptionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    @Mock
    SubscriptionRepository subscriptionRepository;

    @Mock
    PlanRepository planRepository;

    @Mock
    SubscriptionProducer subscriptionProducer;

    @InjectMocks
    SubscriptionService subscriptionService;

    private Plan plan;
    private Subscription subscription;
    private SubscriptionEntryDTO entry;
    private SubscriptionResponse response;

    @BeforeEach
    void setUp() {

        this.plan = new Plan();
        plan.setId("123");

        this.entry = new SubscriptionEntryDTO();
        entry.setPlanId("123");
        entry.setCustomerEmail("emailTest@gmail.com");

        this.subscription = new Subscription(plan, entry.getCustomerEmail());

        this.response = new SubscriptionResponse(subscription.getId(),
                subscription.getStatus(), subscription.getNextBillingDate());
    }

    @Test
    void createSubscription_withValidInput() {

        when(planRepository.findById("123")).thenReturn(Optional.of(plan));

        SubscriptionResponse subResponse = subscriptionService
                .createSubscription(entry);

        assertEquals(response, subResponse);

        assertEquals(response.status(), subResponse.status());

        verify(subscriptionRepository).save(any(Subscription.class));
        verify(subscriptionProducer).sendSubscriptionCreateEvent(any(Subscription.class));

    }

    @Test
    void createSubscription_whenInvalidInput_thorwsPlanNotFound() {

        when(planRepository.findById("123")).thenReturn(Optional.empty());

        PlanNotFound exception = assertThrows(PlanNotFound.class,
                () -> subscriptionService.createSubscription(entry));

        assertEquals("Plano não foi encontrado.", exception.getMessage());

        verify(subscriptionRepository, never()).save(any());
        verify(subscriptionProducer, never()).sendSubscriptionCreateEvent(any());
    }
}
