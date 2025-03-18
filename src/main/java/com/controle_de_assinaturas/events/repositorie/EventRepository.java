package com.controle_de_assinaturas.events.repositorie;

import com.controle_de_assinaturas.events.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, String> {
}
