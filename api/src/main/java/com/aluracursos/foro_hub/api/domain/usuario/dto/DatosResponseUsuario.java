package com.aluracursos.foro_hub.api.domain.usuario.dto;

import com.aluracursos.foro_hub.api.domain.usuario.Usuario;

public record DatosResponseUsuario(
        Long id,
        String nombre,
        String perfil
) {

    public DatosResponseUsuario(Usuario usuario) {

        this(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getPerfil().getNombre()
        );
    }
}
