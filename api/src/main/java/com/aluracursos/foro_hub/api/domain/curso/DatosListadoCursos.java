package com.aluracursos.foro_hub.api.domain.curso;

import com.aluracursos.foro_hub.api.domain.usuario.Usuario;

public record DatosListadoCursos(
        Long id,
        String nombre,
        String categoria
) {
    public DatosListadoCursos(Curso curso) {
        this (curso.getId(), curso.getNombre(), curso.getCategoria());
    }
}
