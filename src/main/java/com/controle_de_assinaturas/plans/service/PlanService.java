package com.controle_de_assinaturas.plans.service;

import com.controle_de_assinaturas.kafka.exceptions.JsonNodeErro;
import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.plans.repositorie.PlanRepository;
import com.controle_de_assinaturas.plans.service.DTOs.PlanEntryDTO;
import com.controle_de_assinaturas.plans.service.DTOs.PlanResponseSubscriptionsStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    private final ObjectMapper objectMapper;

    public PlanService(PlanRepository planRepository,
                       ObjectMapper objectMapper) {

        this.planRepository = planRepository;
        this.objectMapper = objectMapper;
    }

    public void createPlan(PlanEntryDTO plan) {

        Plan newPlan = new Plan(plan);

        planRepository.save(newPlan);
    }

    public PlanResponseSubscriptionsStatus getSubscriptionMetrics() {

        List<Plan> plans = planRepository.findAll();

        int totalAtivas = 0;
        int totalCanceladas = 0;
        List<JsonNode> jsonNodes = new ArrayList<>() ;

        for(Plan plan : plans) {

            String object = String.format("{\"plan_id\": \"%s\", \"name\": " +
                    "\"%s\", \"active_subscriptions\": \"%s\"}",
                    plan.getId(), plan.getName(), plan.getSubscriptions().ativas());

            try{
                JsonNode json = objectMapper.readTree(object);

                jsonNodes.add(json);

                totalAtivas += plan.getSubscriptions().ativas();
                totalCanceladas += plan.getSubscriptions().canceladas();
            } catch (Exception e) {
                throw new JsonNodeErro();
            }
        }

        return new PlanResponseSubscriptionsStatus(totalAtivas,
                totalCanceladas, jsonNodes);
    }
}
