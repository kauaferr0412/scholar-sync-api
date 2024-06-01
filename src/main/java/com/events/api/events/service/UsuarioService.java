package com.events.api.events.service;

import com.events.api.events.dto.EventoDTO;
import com.events.api.events.model.Evento;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private EventoService eventoService;

    public List<Usuario> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<Usuario> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Usuario createUser(Usuario user) {
        return userRepository.save(user);
    }

    public Optional<Usuario> updateUser(Long id, Usuario userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setPassword(userDetails.getPassword());
            user.setEmail(userDetails.getEmail());
            user.setNome(userDetails.getNome());
            return userRepository.save(user);
        });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Usuario addUserToEvent(Usuario user, EventoDTO evento) {
        user.getEventosParticipados().add(eventoService.convertToEntity(evento));
        return userRepository.save(user);
    }
}