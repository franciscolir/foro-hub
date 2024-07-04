package com.aluracursos.foro_hub.api.domain.topico.dto;

import com.aluracursos.foro_hub.api.domain.topico.Estado;
import com.aluracursos.foro_hub.api.domain.topico.Topico;

import java.time.LocalDateTime;

public record DatosListadoTopicos(Long id,

                                  String titulo,

                                  String mensaje,

                                  LocalDateTime fechaCreacion,

                                  Estado status,

                                  int respuestas,

                                  String usuario,

                                  String curso
) {


    public DatosListadoTopicos(Topico topico) {

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
