package com.aluracursos.foro_hub.api.domain.usuario;


import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;


    public DatosResponseUsuario registrar(DatosRegistroUsuario datos) {

        if (repository.existsByCorreoElectronico(datos.correoElectronico())) {
            throw new ValidacionDeIntegridad("este correo electronico ya fue registrado");
        }
        var usuario = new Usuario(datos);
        repository.save(usuario);

        return new DatosResponseUsuario(usuario.getId(), usuario.getNombre(), usuario.getPerfil());
    }

    public DatosResponseUsuario actualizar(DatosActualizaUsuario datos) {

        validaId(datos.id());
        Usuario usuario = usuarioById(datos.id());
        usuario.actualizarInformacion(datos);

        return new DatosResponseUsuario(usuario.getId(), usuario.getNombre(), usuario.getPerfil());
    }

    public String cambiaContraseña(Long id, DatosCambiaContraseñaUsuario datos) {

        if (repository.existsByIdAndContraseña(id, datos.actualContraseña())) {
            Usuario usuario = usuarioById(id);
            usuario.actualizarContraseña(datos);
        } else {

            throw new ValidacionDeIntegridad("datos no coinciden");
        }


        return ("usuario: " + id + " contraseña cambiada");
    }

    public void delete(Long id) {
        validaIdAndActivo(id);
        Usuario usuario = repository.getReferenceById(id);
        usuario.inactivarUsuario();


    }

    public Boolean validaIdAndActivo(Long id) {
        if (!repository.existsByIdAndActivoTrue(id)) {

            throw new ValidacionDeIntegridad("este id de usuario no existe");
        }
    return true;
    }

    public void validaId (Long id){
        if (!repository.findById(id).isPresent()) {
            throw new ValidacionDeIntegridad("este id de usuario no es valido");
        }
    }

    public Usuario usuarioById (Long id){
        Usuario usuario = repository.getReferenceById(id);

        return usuario;
    }

}
