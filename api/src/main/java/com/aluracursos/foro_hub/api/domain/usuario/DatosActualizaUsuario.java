package com.aluracursos.foro_hub.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosActualizaUsuario(

        @NotNull(message = "El id es obligatorio")
        Long id,

        String nombre,

        Perfil perfil) {
}
