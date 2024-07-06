package com.aluracursos.foro_hub.api.domain.topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Page <Topico> findByActivoTrue(Pageable paginacion);

    Boolean existsByIdAndActivoTrue(Long id);

    Boolean existsByIdAndUsuarioId(Long topicoId, Long usuarioId);

    Boolean existsByIdAndCloseTrue(Long id);
}
