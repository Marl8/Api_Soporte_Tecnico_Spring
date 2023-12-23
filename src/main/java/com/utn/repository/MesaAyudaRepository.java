package com.utn.repository;

import com.utn.entity.MesaAyuda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaAyudaRepository extends JpaRepository<MesaAyuda, Long> {
}
