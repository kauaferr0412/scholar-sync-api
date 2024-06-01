package com.events.api.events.controller;

import com.events.api.events.dto.EventoDTO;
import com.events.api.events.model.Evento;
import com.events.api.events.model.Usuario;
import com.events.api.events.service.EventoService;
import com.events.api.events.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @Autowired
    private EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable(value = "id") Long userId) {
        Optional<Usuario> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Usuario> createUser(@RequestBody Usuario user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUser(@PathVariable(value = "id") Long userId,
                                              @RequestBody Usuario userDetails) {
        Optional<Usuario> updatedUser = userService.updateUser(userId, userDetails);
        return updatedUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable(value = "id") Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{userId}/event/{eventId}")
    public ResponseEntity<Usuario> addUserToEvent(@PathVariable(value = "userId") Long userId,
                                                  @PathVariable(value = "eventId") Long eventId) {
        Optional<Usuario> user = userService.getUserById(userId);
        if (user.isPresent()) {
            Optional<EventoDTO> evento = eventoService.getEventoById(eventId);
            if (evento.isPresent()) {
                Usuario updatedUser = userService.addUserToEvent(user.get(), evento.get());
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}