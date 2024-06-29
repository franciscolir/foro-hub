package com.aluracursos.foro_hub.api.domain.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroUsuario(
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,

        @NotBlank(message = "email es obligatorio")
        @Email(message = "Formato de email invalido")
        String correoElectronico,

        @NotBlank(message = "Contraseña es obligatoria")
        String contraseña,

        @NotNull(message = "Datos de perfil son obligatorias")
        Perfil perfil) {
}
