package com.events.api.events.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
public class TrabalhoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String autor;
    private Set<SolucaoDTO> solucoes;
}