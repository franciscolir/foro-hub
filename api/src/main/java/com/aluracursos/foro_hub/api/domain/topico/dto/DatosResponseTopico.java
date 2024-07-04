package com.aluracursos.foro_hub.api.domain.topico.dto;

import com.aluracursos.foro_hub.api.domain.curso.Curso;
import com.aluracursos.foro_hub.api.domain.topico.Estado;
import com.aluracursos.foro_hub.api.domain.topico.Topico;
import com.aluracursos.foro_hub.api.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record DatosResponseTopico(
        Long id,

        String titulo,

        String mensaje,

        LocalDateTime fechaCreacion,

        Estado status,

        int respuestas,

        //Long usuarioId,
        String usuario,
        //Long cursoId
        String curso
) {


    public DatosResponseTopico(Topico topico) {

        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getTotalRespuestas(),
                topico.getUsuario().getNombre(),
                topico.getCurso().getNombre()
        );
    }
}
