package com.aluracursos.foro_hub.api.domain.usuario;


public record DatosResponseUsuario(
        Long id,
        String nombre,
        Perfil perfil
) {
}
