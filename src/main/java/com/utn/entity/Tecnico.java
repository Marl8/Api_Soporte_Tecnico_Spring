package com.utn.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tecnico")
public class Tecnico extends Persona{

    private boolean disponibilidad;

    @Enumerated(EnumType.STRING)
    private MedioNotificacionEnum notificacion;

    @ManyToMany(mappedBy = "listaTecnicos", cascade = CascadeType.MERGE)
    private Set<Especialidad> listaEspecialidades = new HashSet<>();

    @OneToMany(mappedBy = "tecnico")
    private Set<Incidente> incidentes;

    @Override
    public String toString() {
        return "Tecnico{" + super.toString() + " disponibilidad=" + disponibilidad + ", notificacion=" + notificacion + '}';
    }

}
