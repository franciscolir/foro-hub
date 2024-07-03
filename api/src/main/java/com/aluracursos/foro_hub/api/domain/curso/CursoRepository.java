package com.aluracursos.foro_hub.api.domain.curso;

import com.aluracursos.foro_hub.api.domain.topico.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
