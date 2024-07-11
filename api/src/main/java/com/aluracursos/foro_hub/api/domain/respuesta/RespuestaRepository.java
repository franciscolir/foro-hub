package com.aluracursos.foro_hub.api.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

    Page<Respuesta> findByActivoTrue(Pageable paginacion);

    Boolean existsByIdAndActivoTrue(Long id);

    @Query("SELECT r FROM Respuesta r WHERE r.autorRespuesta.id = :usuarioId AND r.activo = true")
   Page<Respuesta> findByAutorRespuestaAndActivoTrue(Long usuarioId, Pageable paginacion);
}
