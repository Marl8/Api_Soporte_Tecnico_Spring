package com.utn.repository;

import com.utn.entity.TipoProblema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemaRepository extends JpaRepository<TipoProblema, Long> {
}
