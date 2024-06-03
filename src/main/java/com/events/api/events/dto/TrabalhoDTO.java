package com.events.api.events.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;


@Builder
@Getter
@Setter
public class TrabalhoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String autor;
    private Set<SolucaoDTO> solucoes;

    public TrabalhoDTO() {
    }

    public TrabalhoDTO(Long id, String titulo, String descricao, String autor, Set<SolucaoDTO> solucoes) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.autor = autor;
        this.solucoes = solucoes;
    }

    public TrabalhoDTO(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }
}