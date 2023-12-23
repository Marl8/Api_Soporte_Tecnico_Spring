package com.utn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "operador")
public class Operador extends Persona{

    /*@OneToOne(targetEntity = Incidente.class)
    @JoinColumn(name = "fk_incidente", referencedColumnName = "id")
    private Incidente incidente;*/

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_operador", referencedColumnName = "id")
    private Set<Tecnico> listaTecnicos;
}
