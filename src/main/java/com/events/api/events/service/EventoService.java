package com.events.api.events.service;


import com.events.api.events.dto.EventoDTO;
import com.events.api.events.dto.UsuarioDTO;
import com.events.api.events.enumerator.TipoEvento;
import com.events.api.events.model.Evento;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.EventoRepository;
import com.events.api.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<EventoDTO> getAllEventos() {
        return eventoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EventoDTO> getEventoById(Long id) {
        return eventoRepository.findById(id).map(this::convertToDTO);
    }

    public EventoDTO createEvento(EventoDTO eventoDTO, String username) {
        Usuario organizador = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Evento evento = convertToEntity(eventoDTO);
        evento.setOrganizador(organizador);
        evento = eventoRepository.save(evento);
        return convertToDTO(evento);
    }

    public Optional<EventoDTO> updateEvento(Long id, EventoDTO eventoDTO) {
        return eventoRepository.findById(id).map(evento -> {
            evento.setTitulo(eventoDTO.getTitulo());
            evento.setDescricao(eventoDTO.getDescricao());
            evento.setLocalizacao(eventoDTO.getLocalizacao());
            evento.setDataInicio(eventoDTO.getDataInicio());
            evento.setDataFim(eventoDTO.getDataFim());
            evento.setTipo(TipoEvento.valueOf(eventoDTO.getTipo()));
            return convertToDTO(eventoRepository.save(evento));
        });
    }

    public void deleteEvento(Long id) {
        eventoRepository.deleteById(id);
    }

    public EventoDTO inscreverUsuarioEmEvento(Long eventoId, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Evento evento = eventoRepository.findById(eventoId).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        evento.getParticipantes().add(usuario);
        usuario.getEventosParticipados().add(evento);
        eventoRepository.save(evento);
        return convertToDTO(evento);
    }

    public EventoDTO convertToDTO(Evento evento) {
        EventoDTO eventoDTO = new EventoDTO();
        eventoDTO.setId(evento.getId());
        eventoDTO.setTitulo(evento.getTitulo());
        eventoDTO.setDescricao(evento.getDescricao());
        eventoDTO.setLocalizacao(evento.getLocalizacao());
        eventoDTO.setDataInicio(evento.getDataInicio());
        eventoDTO.setDataFim(evento.getDataFim());
        eventoDTO.setTipo(evento.getTipo().toString());
        eventoDTO.setOrganizador(convertToDTO(evento.getOrganizador()));
        if(Objects.nonNull(evento.getParticipantes()) && !evento.getParticipantes().isEmpty()) {
            eventoDTO.setParticipantes(evento.getParticipantes().stream().map(this::convertToDTO).collect(Collectors.toList()));
        }
        return eventoDTO;
    }

    public UsuarioDTO convertToDTO(Usuario usuario) {
       UsuarioDTO usuarioDTO = new UsuarioDTO();
       usuarioDTO.setId(usuario.getId());
       usuarioDTO.setUsername(usuario.getUsername());
       usuarioDTO.setEmail(usuario.getEmail());
       usuarioDTO.setNome(usuario.getNome());
        return usuarioDTO;
    }

    public Evento convertToEntity(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        if(Objects.nonNull(eventoDTO.getId())){
            evento.setId(eventoDTO.getId());
        }
        evento.setTitulo(eventoDTO.getTitulo());
        evento.setDescricao(eventoDTO.getDescricao());
        evento.setLocalizacao(eventoDTO.getLocalizacao());
        evento.setDataInicio(eventoDTO.getDataInicio());
        evento.setDataFim(eventoDTO.getDataFim());
        evento.setTipo(TipoEvento.valueOf(eventoDTO.getTipo()));
        return evento;
    }
}