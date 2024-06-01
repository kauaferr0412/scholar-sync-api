package com.events.api.events.repository;

import com.events.api.events.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    @Query("SELECT e FROM Evento e JOIN FETCH e.participantes WHERE e.id = :eventoId")
    Optional<Evento> findByIdWithParticipantes(@Param("eventoId") Long eventoId);
}