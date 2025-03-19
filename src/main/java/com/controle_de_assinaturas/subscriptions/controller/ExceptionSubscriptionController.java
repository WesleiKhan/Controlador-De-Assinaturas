package com.controle_de_assinaturas.subscriptions.controller;

import com.controle_de_assinaturas.kafka.exceptions.JsonNodeErro;
import com.controle_de_assinaturas.plans.exceptions.PlanNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice(basePackages = "com.controle_de_assinaturas.subscriptions.controller")
public class ExceptionSubscriptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlanNotFound.class)
    private ResponseEntity<String> planNotFount(PlanNotFound e) {

        Map<String, String> response = new HashMap<>();
        response.put("Message", e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.get("Message"));
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<String> runtimeExceptiom(RuntimeException e) {

        Map<String, String> response = new HashMap<>();
        response.put("Message", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.get(
                "Message"));
    }

    @ExceptionHandler(JsonNodeErro.class)
    private ResponseEntity<String> jsonNodeErro(JsonNodeErro e) {

        Map<String, String> response = new HashMap<>();
        response.put("Message", e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.get(
                "Message"));
    }
}
