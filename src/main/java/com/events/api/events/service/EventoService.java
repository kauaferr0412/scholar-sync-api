package com.events.api.events.service;


import com.events.api.events.dto.EventoDTO;
import com.events.api.events.dto.UsuarioDTO;
import com.events.api.events.enumerator.TipoEvento;
import com.events.api.events.model.Evento;
import com.events.api.events.model.Frequencia;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.EventoRepository;
import com.events.api.events.repository.FrequenciaRepository;
import com.events.api.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Autowired
    private FrequenciaRepository frequenciaRepository;

    public List<EventoDTO> getAllEventos() {
        return eventoRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EventoDTO> getEventosNaoInscritosParaUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Evento> eventos = eventoRepository.findAll().stream()
                .filter(evento -> !evento.getParticipantes().contains(usuario))
                .collect(Collectors.toList());

        return eventos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<EventoDTO> getEventosCriadosPorUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Evento> eventos = eventoRepository.findByOrganizador(usuario);

        return eventos.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    public List<EventoDTO> searchEventos(String titulo, Boolean inscritos, String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Evento> eventos;
        if (inscritos != null && inscritos) {
            eventos = eventoRepository.findByParticipantesId(usuario.getId());
        } else {
            eventos = eventoRepository.findByTituloContainingIgnoreCase(titulo);
        }

        return eventos.stream().map(this::convertToDTO).collect(Collectors.toList());
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

    @Transactional
    public void deleteEvento(Long id) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        evento.getParticipantes().forEach(usuario -> {
            usuario.getEventosParticipados().remove(evento);
            usuarioRepository.save(usuario);
        });


        List<Frequencia> frequencias = frequenciaRepository.findByEvento(evento);
        frequencias.forEach(frequencia -> {frequenciaRepository.deleteById(frequencia.getId());});


        evento.setParticipantes(new HashSet<>());
        eventoRepository.save(evento);
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