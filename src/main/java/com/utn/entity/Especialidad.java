package com.utn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "especialidad")
public class Especialidad{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "especialidad", fetch = FetchType.EAGER)
    private Set<TipoProblema> listaProblemas = new HashSet<>();

    @ManyToMany(targetEntity = Tecnico.class, cascade = CascadeType.MERGE)
    @JoinTable(name = "especialidad-tecnico", joinColumns = @JoinColumn(name = "fk_especialidad"),
            inverseJoinColumns = @JoinColumn(name = "fk_tecnico"))
    private Set<Tecnico> listaTecnicos = new HashSet<>();
}
