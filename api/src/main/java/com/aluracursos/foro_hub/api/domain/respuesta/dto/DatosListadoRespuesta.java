package com.aluracursos.foro_hub.api.domain.respuesta.dto;

import com.aluracursos.foro_hub.api.domain.respuesta.Respuesta;
import com.aluracursos.foro_hub.api.domain.topico.Topico;

import java.time.LocalDateTime;

public record DatosListadoRespuesta(
        Long id,

        String mensaje,

        Long topico,

        LocalDateTime fechaCreacion,

        Long autorRespuesta,

        String solucion
) {

    public DatosListadoRespuesta(Respuesta respuesta) {

        this(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getTopico().getId(),
                respuesta.getFechaCreacion(),
                respuesta.getAutorRespuesta().getId(),
                respuesta.getSolucion()
        );
    }
}

