package com.events.api.events.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "nome_de_usuario")
    private String username;

    @Column(nullable = false, name = "senha")
    private String password;

    @Column
    private String email;

    @Column
    private String nome;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "organizador", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Evento> eventos;

    @JsonIgnoreProperties("participantes") // Evitar ciclo de serialização JSON
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "participantes_eventos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "evento_id")
    )
    @JsonBackReference // Correção para evitar referência gerenciada inadequadamente
    private Set<Evento> eventosParticipados = new HashSet<>();

    @OneToMany(mappedBy = "autor", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnore
    private Set<Trabalho> trabalhos;
}
