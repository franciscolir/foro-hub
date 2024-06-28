package com.aluracursos.foro_hub.api.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosPerfil(

        @NotNull(message = "id perfil es obligatorio")
        Long id
) {
}
