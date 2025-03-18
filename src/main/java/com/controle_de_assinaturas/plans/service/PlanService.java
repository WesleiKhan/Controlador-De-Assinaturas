package com.controle_de_assinaturas.plans.service;

import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.plans.repositorie.PlanRepository;
import com.controle_de_assinaturas.plans.service.DTOs.PlanEntryDTO;
import org.springframework.stereotype.Service;

@Service
public class PlanService {

    private final PlanRepository planRepository;

    public PlanService(PlanRepository planRepository) {

        this.planRepository = planRepository;
    }

    public void createPlan(PlanEntryDTO plan) {

        Plan newPlan = new Plan(plan);

        planRepository.save(newPlan);
    }
}
