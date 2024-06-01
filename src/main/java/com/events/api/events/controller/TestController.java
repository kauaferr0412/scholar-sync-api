package com.events.api.events.controller;


import com.events.api.events.dto.CadastroDTO;
import com.events.api.events.dto.JwtResponseDTO;
import com.events.api.events.dto.LoginDTO;
import com.events.api.events.dto.MessageResponseDTO;
import com.events.api.events.enumerator.ERole;
import com.events.api.events.model.Role;
import com.events.api.events.model.Usuario;
import com.events.api.events.repository.RoleRepository;
import com.events.api.events.repository.UsuarioRepository;
import com.events.api.events.security.JwtUtils;
import com.events.api.events.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teste")
public class TestController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping
    public String authenticateUser( ) {
       return "NO AR!";
    }


}