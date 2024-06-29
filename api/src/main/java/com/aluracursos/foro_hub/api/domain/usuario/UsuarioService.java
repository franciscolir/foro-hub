package com.aluracursos.foro_hub.api.domain.usuario;


import com.aluracursos.foro_hub.api.controller.UsuarioRepository;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;


    public DatosResponseUsuario  validar(DatosRegistroUsuario datos) {

        if (repository.existsByCorreoElectronico(datos.correoElectronico())) {
            throw new ValidacionDeIntegridad("este correo electronico ya fue registrado");
        }
        var usuario = new Usuario(datos);
        repository.save(usuario);
        return new DatosResponseUsuario(usuario.getId(), usuario.getNombre(),usuario.getPerfil());
    }
}
