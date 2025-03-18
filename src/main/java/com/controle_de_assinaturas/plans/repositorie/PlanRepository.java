package com.controle_de_assinaturas.plans.repositorie;

import com.controle_de_assinaturas.plans.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {
}
