package com.events.api.events.model;

import com.events.api.events.enumerator.ERole;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "nome")
    private ERole name;

}
