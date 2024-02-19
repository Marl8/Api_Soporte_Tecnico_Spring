package com.utn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "especialidad")
public class Especialidad{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "especialidad", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<TipoProblema> listaProblemas = new HashSet<>();

    @ManyToMany(mappedBy = "listaEspecialidades", cascade = CascadeType.ALL)
    private Set<Tecnico> listaTecnicos;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Especialidad that = (Especialidad) o;
        return Objects.equals(id, that.id) && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(listaProblemas, that.listaProblemas) && Objects.equals(listaTecnicos, that.listaTecnicos);
    }

    @Override
    public String toString() {
        return "Especialidad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, listaProblemas, listaTecnicos);
    }
}
