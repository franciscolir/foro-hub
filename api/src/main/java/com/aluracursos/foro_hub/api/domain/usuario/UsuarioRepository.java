package com.aluracursos.foro_hub.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Boolean existsByCorreoElectronico(String correoElectronico);

    boolean existsByIdAndContraseña(Long id, String contraseña);


    boolean existsByIdAndActivoTrue(Long id);
    //boolean existsActivoTrueById(Long id);

}
