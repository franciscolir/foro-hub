package com.aluracursos.foro_hub.api.domain.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosCambiaContraseñaUsuario(

        @NotBlank (message = "El campo es obligatorio")
        String actualContraseña,
        @NotBlank (message = "El campo es obligatorio")
        String nuevaContraseña
) {
}
