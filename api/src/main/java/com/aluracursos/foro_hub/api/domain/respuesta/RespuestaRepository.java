package com.aluracursos.foro_hub.api.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

    Page<Respuesta> findByActivoTrue(Pageable paginacion);

    Boolean existsByIdAndActivoTrue(Long id);
}
