package com.events.api.events.repository;

import com.events.api.events.model.Evento;
import com.events.api.events.model.Frequencia;
import com.events.api.events.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FrequenciaRepository extends JpaRepository<Frequencia, Long> {
    List<Frequencia> findByAluno(Usuario aluno);
    List<Frequencia> findByEvento(Evento evento);

    Optional<Frequencia> findByAlunoAndEvento(Usuario aluno, Evento evento);

}