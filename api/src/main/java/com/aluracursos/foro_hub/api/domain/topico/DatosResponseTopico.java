package com.aluracursos.foro_hub.api.domain.topico;

import java.time.LocalDateTime;

public record DatosResponseTopico(
        Long id,

        String titulo,

        String mensaje,

        LocalDateTime fechaCreacion,

        Estado status,

        int respuestas,

        Long usuarioId,

        Long cursoId
) {


    public DatosResponseTopico(Topico topico) {

        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getTotalRespuestas(),
                topico.getUsuario().getId(),
                topico.getCurso().getId()
        );
    }
}
