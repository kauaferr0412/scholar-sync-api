package com.events.api.events.repository;

import com.events.api.events.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u " +
            "LEFT JOIN FETCH u.roles " +
            "LEFT JOIN FETCH u.eventos " +
            "LEFT JOIN FETCH u.eventosParticipados " +
            "LEFT JOIN FETCH u.trabalhos")
    Set<Usuario> pegarTodos();
    Optional<Usuario> findByUsername(String username);
    Boolean existsByUsername(String username);
}