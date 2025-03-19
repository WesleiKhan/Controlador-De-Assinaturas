package com.controle_de_assinaturas.kafka.exceptions;

public class JsonNodeErro extends RuntimeException{

    public JsonNodeErro() {super("Erro no JsonNode");}

    public JsonNodeErro(String message) {super(message);}
}
