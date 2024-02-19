package com.utn.repository;

import com.utn.entity.EEstado;
import com.utn.entity.Incidente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenteRepository extends JpaRepository<Incidente, Long> {

    @Query("SELECT i FROM Incidente i WHERE i.fechaCierre >= ?1 AND i.estado = ?2")
    List<Incidente> findIncidenteByEstadoAndFecha(LocalDateTime fechaBusqueda, EEstado estado);

    List<Incidente> findIncidenteByEstado(EEstado estado);
}
