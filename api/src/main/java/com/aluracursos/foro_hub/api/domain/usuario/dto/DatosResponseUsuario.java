package com.aluracursos.foro_hub.api.domain.usuario.dto;


import com.aluracursos.foro_hub.api.domain.usuario.Perfil;

public record DatosResponseUsuario(
        Long id,
        String nombre,
        Perfil perfil
) {
}
