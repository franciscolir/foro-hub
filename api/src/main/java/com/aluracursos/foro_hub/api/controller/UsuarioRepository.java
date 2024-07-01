package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Boolean existsByCorreoElectronico(String correoElectronico);

    boolean existsByIdAndContraseña(Long id, String contraseña);


    boolean existsByIdAndActivoTrue(Long id);
    //boolean existsActivoTrueById(Long id);

}
