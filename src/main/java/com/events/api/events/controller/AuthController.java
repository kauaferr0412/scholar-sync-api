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
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
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

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponseDTO(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody CadastroDTO signUpRequest) {
        validarDTO(signUpRequest);

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new RuntimeException("Nome de usuário já utilizado!");
        }

        Usuario user = Usuario.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .nome(signUpRequest.getNome())
                .email(signUpRequest.getEmail())
                .build();

        Set<String> strRoles = signUpRequest.getRole();

        user.setRoles(validateRules(strRoles));
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponseDTO("Usuário registrado com sucesso!"));
    }


    private Set<Role> validateRules(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: O usuário não possui um perfil"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Perfil não encontrado."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Perfil não encontrado."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Perfil não encontrado."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }

    private void validarDTO(CadastroDTO signUpRequest) {
        if(Objects.isNull(signUpRequest.getEmail()) || signUpRequest.getEmail().isBlank()) {
            throw  new RuntimeException("O e-mail não pode estar vazio");
        }

        if(Objects.isNull(signUpRequest.getNome()) || signUpRequest.getNome().isBlank()) {
            throw  new RuntimeException("O nome não pode estar vazio");
        }

        if(Objects.isNull(signUpRequest.getRole()) || signUpRequest.getRole().isEmpty()) {
            throw  new RuntimeException("O usuário precisa de uma ocupação");
        }
    }


}