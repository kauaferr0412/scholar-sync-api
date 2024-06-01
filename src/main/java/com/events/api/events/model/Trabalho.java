package com.events.api.events.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trabalho")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Trabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @OneToMany(mappedBy = "trabalho", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Solucao> solucoes = new HashSet<>();
}