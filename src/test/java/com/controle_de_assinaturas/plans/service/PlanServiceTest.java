package com.controle_de_assinaturas.plans.service;

import com.controle_de_assinaturas.kafka.exceptions.JsonNodeErro;
import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.plans.repositorie.PlanRepository;
import com.controle_de_assinaturas.plans.service.DTOs.PlanEntryDTO;
import com.controle_de_assinaturas.plans.service.DTOs.PlanResponseSubscriptionsStatus;
import com.controle_de_assinaturas.subscriptions.entity.Status;
import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlanServiceTest {

    @Mock
    PlanRepository planRepository;

    @Mock
    ObjectMapper objectMapper;

    @InjectMocks
    PlanService planService;

    private Plan plan;
    private List<Plan> plans;
    private PlanEntryDTO entry;
    private PlanResponseSubscriptionsStatus response;


    @BeforeEach
    void setUp() {

        this.entry = new PlanEntryDTO();
        entry.setName("Plan Test");
        entry.setPrice(new BigDecimal(100));

        Subscription subscription = new Subscription(new Plan(entry),
                "testgmail.com");
        subscription.setStatus(Status.Ativa);

        List<Subscription> subscriptions = new ArrayList<>();
        subscriptions.add(subscription);

        this.plan = new Plan(entry);
        plan.setSubscriptions(subscriptions);

        this.plans = new ArrayList<>();
        plans.add(plan);

        List<JsonNode> jsonNodes = new ArrayList<>();

        String object = String.format("{\"plan_id\": \"%s\", \"name\": " +
                        "\"%s\", \"active_subscriptions\": \"%s\"}",
                plan.getId(), plan.getName(), plan.getSubscriptions().ativas());

        int ativas = plan.getSubscriptions().ativas();
        int canceladas = plan.getSubscriptions().canceladas();

        try{
            JsonNode json = objectMapper.readTree(object);

            jsonNodes.add(json);


        } catch (Exception e) {
            throw new JsonNodeErro();
        }

        this.response = new PlanResponseSubscriptionsStatus(ativas,
                canceladas, jsonNodes);
    }

    @Test
    void createPlan_withValidInput() {

        planService.createPlan(entry);

        verify(planRepository).save(any(Plan.class));
    }

    @Test
    void getSubscriptionMetrics_withValidInput() {

        when(planRepository.findAll()).thenReturn(plans);

        PlanResponseSubscriptionsStatus planResponse =
                planService.getSubscriptionMetrics();

        assertEquals(response, planResponse);

        assertEquals(response.plans(), planResponse.plans());
    }
}
