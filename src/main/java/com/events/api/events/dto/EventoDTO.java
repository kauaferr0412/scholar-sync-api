package com.events.api.events.dto;

import com.events.api.events.enumerator.TipoEvento;
import com.events.api.events.model.Usuario;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class EventoDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private String localizacao;
    private Date dataInicio;
    private Date dataFim;
    private String tipo;
    private UsuarioDTO organizador;
    private List<UsuarioDTO> participantes;
}