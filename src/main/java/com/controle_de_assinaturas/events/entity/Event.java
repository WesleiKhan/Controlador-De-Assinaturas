package com.controle_de_assinaturas.events.entity;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;


@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(Types.OTHER)
    private JsonNode data;

    @Column(name = "processed")
    private boolean processed;

    public Event() {
    }

    public Event(Type type, JsonNode data, boolean processed) {
        this.type = type;
        this.data = data;
        this.processed = processed;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
