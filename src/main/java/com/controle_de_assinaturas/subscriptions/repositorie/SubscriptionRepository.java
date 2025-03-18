package com.controle_de_assinaturas.subscriptions.repositorie;

import com.controle_de_assinaturas.subscriptions.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,
        String> {
}
