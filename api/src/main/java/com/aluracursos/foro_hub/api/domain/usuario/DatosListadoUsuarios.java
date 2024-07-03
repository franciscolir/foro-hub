package com.aluracursos.foro_hub.api.domain.usuario;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record DatosListadoUsuarios (

        Long id,

        String nombre,

        String correoElectronico,

        String perfil
){
    public DatosListadoUsuarios(Usuario usuario) {
        this (usuario.getId(), usuario.getNombre(), usuario.getCorreoElectronico(), usuario.getPerfil().toString());
    }
}
