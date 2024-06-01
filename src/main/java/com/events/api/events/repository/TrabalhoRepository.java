package com.events.api.events.repository;

import com.events.api.events.model.Trabalho;
import com.events.api.events.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface TrabalhoRepository extends JpaRepository<Trabalho, Long> {
    List<Trabalho> findByAutor(Usuario autor);
}