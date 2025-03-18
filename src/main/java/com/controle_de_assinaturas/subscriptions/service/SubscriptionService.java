package com.controle_de_assinaturas.subscriptions.service;

import com.controle_de_assinaturas.plans.entity.Plan;
import com.controle_de_assinaturas.plans.repositorie.PlanRepository;
import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import com.controle_de_assinaturas.subscriptions.repositorie.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    private final PlanRepository planRepository;

    public SubscriptionService (SubscriptionRepository subscriptionRepository,
                                PlanRepository planRepository) {

        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }

    public SubscriptionResponse createSubscription(SubscriptionEntryDTO data) {

        Optional<Plan> optionalPlan = planRepository.findById(data.getPlanId());

        if(optionalPlan.isPresent()) {

            Plan plan = optionalPlan.get();

            Subscription newSubscription = new Subscription(plan,
                    data.getCustomerEmail());

            subscriptionRepository.save(newSubscription);

            return new SubscriptionResponse(newSubscription.getId(),
                    newSubscription.getStatus(),
                    newSubscription.getNextBillingDate());

        } else {

            throw new RuntimeException();
        }
    }
}
