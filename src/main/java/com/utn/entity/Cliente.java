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
@Table(name = "cliente")
public class Cliente extends Persona{

    @Column(name = "razon_social")
    private String razonSocial;
    private String cuit;
    private String telefono;
    @Column(name = "email")
    private String correoElectronico;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Incidente> incidentes;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "cliente_servicio", joinColumns = @JoinColumn(name = "fk_cliente"),
            inverseJoinColumns = @JoinColumn(name = "fk_servicio"))
    private Set<Servicio> servicios = new HashSet<>();

    @Override
    public String toString() {
        return "Cliente{" + "razonSocial=" + razonSocial + ", cuit=" + cuit + ", telefono=" + telefono + ", correoElectronico=" + correoElectronico + '}';
    }
}
