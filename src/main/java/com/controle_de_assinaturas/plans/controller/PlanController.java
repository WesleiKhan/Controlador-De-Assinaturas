package com.controle_de_assinaturas.plans.controller;

import com.controle_de_assinaturas.plans.service.DTOs.PlanEntryDTO;
import com.controle_de_assinaturas.plans.service.DTOs.PlanResponseSubscriptionsStatus;
import com.controle_de_assinaturas.plans.service.PlanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    public PlanController(PlanService planService) {

        this.planService = planService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPlan(@RequestBody PlanEntryDTO data) {

        planService.createPlan(data);

        return ResponseEntity.ok().body("");
    }

    @GetMapping("/reports/subscriptions")
    public ResponseEntity<PlanResponseSubscriptionsStatus> getStatusSubscriptionStatus() {

        PlanResponseSubscriptionsStatus status =
                planService.getSubscriptionMetrics();

        return ResponseEntity.ok().body(status);
    }
}
