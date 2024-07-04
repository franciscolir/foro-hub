package com.aluracursos.foro_hub.api.domain.curso;

import com.aluracursos.foro_hub.api.domain.topico.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findByActivoTrue(Pageable paginacion);

    Boolean existsByIdAndActivoTrue(Long id);

    Boolean existsByIdAndActivoFalse(Long id);
}
