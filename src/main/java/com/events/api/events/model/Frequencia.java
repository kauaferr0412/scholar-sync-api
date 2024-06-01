package com.events.api.events.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "frequencia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Frequencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id", nullable = false)
    private Usuario aluno;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(nullable = false)
    private LocalDateTime dataHoraRegistro;
}