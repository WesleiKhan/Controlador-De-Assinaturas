package com.controle_de_assinaturas.subscriptions.execeptions;

public class SubscriptionNotFound extends RuntimeException{

    public SubscriptionNotFound() {super("Assinatura não foi encontrada.");}

    public SubscriptionNotFound(String message) {super(message);}
}
