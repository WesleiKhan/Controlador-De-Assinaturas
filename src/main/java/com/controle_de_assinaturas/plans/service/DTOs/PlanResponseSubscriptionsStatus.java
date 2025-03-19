package com.controle_de_assinaturas.plans.service.DTOs;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public record PlanResponseSubscriptionsStatus(int total_active,
                                              int total_cancelled,
                                              List<JsonNode> plans) {
}
