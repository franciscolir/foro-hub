package com.aluracursos.foro_hub.api.domain.usuario;

import com.aluracursos.foro_hub.api.domain.perfil.PerfilRepository;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosActualizaUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosCambiaContraseñaUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosRegistroUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosResponseUsuario;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;
    @Autowired
    PerfilRepository perfilRepository;

    //Registra un nuevo usuario
    public DatosResponseUsuario registrar(DatosRegistroUsuario datos) {
        validaUsuarioEmailAndActivoFalse(datos.correoElectronico());
        validaUsuarioEmail(datos.correoElectronico());
        var perfil = perfilRepository.findById(datos.perfilId()).get();
        var usuario = new Usuario(null, datos.nombre(), datos.correoElectronico(), datos.contraseña(), perfil, true);
        repository.save(usuario);

        return new DatosResponseUsuario(usuario);
    }

    //Actualiza un usuario registrado por id
    public DatosResponseUsuario actualizar(DatosActualizaUsuario datos) {
        validaUsuarioIdAndActivo(datos.id());
        var usuario = usuarioById(datos.id());
        var perfil = perfilRepository.getReferenceById(datos.perfilId());
        usuario.actualizarInformacion(datos.id(),datos.nombre(),perfil);

        return new DatosResponseUsuario(usuario);
    }

    //Cambia contraseña de usuario
    public String cambiaContraseña(Long id, DatosCambiaContraseñaUsuario datos) {
        if (repository.existsByIdAndContraseña(id, datos.actualContraseña())) {
            var usuario = usuarioById(id);
            usuario.actualizarContraseña(datos);
        } else {
            throw new ValidacionDeIntegridad("datos no coinciden");
        }
        return ("usuario: " + id + " contraseña cambiada");
    }

    //Inactiva usuario
    public void delete(Long id) {
        validaUsuarioIdAndActivo(id);
        var usuario = usuarioById(id);
        usuario.inactivarUsuario();
    }

    //Metodos de validacion y referencia
    public void validaUsuarioEmailAndActivoFalse(String correoElectronico){
        if (repository.existsByCorreoElectronicoAndActivoFalse(correoElectronico)) {
            throw new ValidacionDeIntegridad("el correo electronico ya fue registrado, pero esta inactivo");
        }
    }

    public void validaUsuarioEmail(String correoElectronico) {
        if (repository.existsByCorreoElectronico(correoElectronico)) {
            throw new ValidacionDeIntegridad("este correo electronico ya fue registrado");
        }
    }

    public void validaUsuarioIdAndActivo(Long id) {
        if (!repository.existsByIdAndActivoTrue(id)) {
            throw new ValidacionDeIntegridad("este id de usuario no existe");
        }
    }

    public Usuario usuarioById (Long id){
        var usuario = repository.getReferenceById(id);

        return usuario;
    }

}
