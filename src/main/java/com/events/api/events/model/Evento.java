package com.events.api.events.model;


import com.events.api.events.enumerator.TipoEvento;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "eventos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    private String localizacao;

    private Date dataInicio;

    private Date dataFim;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false, name = "tipo_evento")
    private TipoEvento tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario organizador;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "evento_participantes",
            joinColumns = @JoinColumn(name = "evento_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> participantes = new HashSet<>();
}
