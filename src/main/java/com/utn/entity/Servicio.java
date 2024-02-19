package com.utn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "servicio")
public class Servicio{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_servicio")
    private String tipoServicio;

    private String descripcion;

    @ManyToMany(mappedBy = "servicios")
    private Set<Cliente> clientes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return Objects.equals(id, servicio.id) && Objects.equals(tipoServicio, servicio.tipoServicio) && Objects.equals(descripcion, servicio.descripcion) && Objects.equals(clientes, servicio.clientes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoServicio, descripcion, clientes);
    }

    @Override
    public String toString() {
        return "Servicio{" +
                "id=" + id +
                ", tipoServicio='" + tipoServicio + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
