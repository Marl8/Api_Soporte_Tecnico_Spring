package com.utn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    public void agregarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }
}
