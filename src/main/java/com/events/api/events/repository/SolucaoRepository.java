package com.events.api.events.repository;

import com.events.api.events.model.Solucao;
import com.events.api.events.model.Trabalho;
import com.events.api.events.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolucaoRepository extends JpaRepository<Solucao, Long> {
    List<Solucao> findByTrabalho(Trabalho trabalho);
    List<Solucao> findByAluno(Usuario aluno);
}