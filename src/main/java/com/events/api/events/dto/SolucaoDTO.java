package com.events.api.events.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SolucaoDTO {

    private Long id;
    private String caminhoArquivo;
    private LocalDateTime dataSubmissao;
    private String comentarioAvaliacao;
    private Integer notaAvaliacao;
    private String aluno;
    private Long trabalhoId;
}