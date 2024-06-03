package com.events.api.events.controller;

import com.events.api.events.dto.EventoDTO;
import com.events.api.events.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public List<EventoDTO> getAllEventos() {
        return eventoService.getAllEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> getEventoById(@PathVariable Long id) {
        Optional<EventoDTO> evento = eventoService.getEventoById(id);
        return evento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public EventoDTO createEvento(@RequestBody EventoDTO eventoDTO, Authentication authentication) {
        String username = authentication.getName();
        return eventoService.createEvento(eventoDTO, username);
    }

    @GetMapping("/search")
    public List<EventoDTO> searchEventos(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Boolean inscritos,
            Authentication authentication
    ) {
        String username = authentication.getName();
        return eventoService.searchEventos(titulo, inscritos, username);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> updateEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Optional<EventoDTO> updatedEvento = eventoService.updateEvento(id, eventoDTO);
        return updatedEvento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable Long id) {
        eventoService.deleteEvento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/inscrever")
    public ResponseEntity<EventoDTO> inscreverUsuarioEmEvento(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        EventoDTO eventoDTO = eventoService.inscreverUsuarioEmEvento(id, username);
        return ResponseEntity.ok(eventoDTO);
    }
}