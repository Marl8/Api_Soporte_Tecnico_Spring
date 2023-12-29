package com.utn.repository;

import com.utn.dto.request.TecnicoDto;
import com.utn.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    Optional<Tecnico> findTecnicoByNombreAndApellido(String nombre, String apellido);
}
