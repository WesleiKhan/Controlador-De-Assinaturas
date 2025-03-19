package com.controle_de_assinaturas.plans.exceptions;

public class PlanNotFound extends RuntimeException{

    public PlanNotFound() {super("Plano não foi encontrado.");}

    public PlanNotFound(String message) {super(message);}
}
