package com.events.api.events.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class FrequenciaDTO {

    private Long id;
    private String nomeParticipante;
    private Long idEvento;
    private LocalDateTime dataHora;
}