package com.events.api.events.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solucao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Solucao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String caminhoArquivo;

    @Column(nullable = false)
    private LocalDateTime dataSubmissao;

    @Column(columnDefinition = "TEXT")
    private String comentarioAvaliacao;

    @Column
    private Double notaAvaliacao;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "trabalho_id", nullable = false)
    private Trabalho trabalho;
}