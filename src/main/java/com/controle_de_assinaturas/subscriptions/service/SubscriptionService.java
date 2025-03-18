package com.controle_de_assinaturas.subscriptions.service;

import com.controle_de_assinaturas.kafka.producer.SubscriptionProducer;
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

    private final SubscriptionProducer subscriptionProducer;

    public SubscriptionService (SubscriptionRepository subscriptionRepository,
                                PlanRepository planRepository,
                                SubscriptionProducer subscriptionProducer) {

        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
        this.subscriptionProducer = subscriptionProducer;
    }

    public SubscriptionResponse createSubscription(SubscriptionEntryDTO data) {

        Optional<Plan> optionalPlan = planRepository.findById(data.getPlanId());

        if(optionalPlan.isPresent()) {

            Plan plan = optionalPlan.get();

            Subscription newSubscription = new Subscription(plan,
                    data.getCustomerEmail());

            subscriptionRepository.save(newSubscription);

            subscriptionProducer.sendSubscriptionCreateEvent(newSubscription);

            return new SubscriptionResponse(newSubscription.getId(),
                    newSubscription.getStatus(),
                    newSubscription.getNextBillingDate());

        } else {

            throw new RuntimeException();
        }
    }
}
