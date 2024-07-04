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


    public DatosResponseUsuario registrar(DatosRegistroUsuario datos) {
        if (repository.existsByCorreoElectronicoAndActivoFalse(datos.correoElectronico())) {
            throw new ValidacionDeIntegridad("el correo electronico ya fue registrado, pero esta inactivo");
        }
        if (repository.existsByCorreoElectronico(datos.correoElectronico())) {
            throw new ValidacionDeIntegridad("este correo electronico ya fue registrado");
        }
        var perfil = perfilRepository.findById(datos.perfilId()).get();

        var usuario = new Usuario(
                null,
                datos.nombre(),
                datos.correoElectronico(),
                datos.contraseña(),
                perfil,
                true);
        repository.save(usuario);

        return new DatosResponseUsuario(usuario);
    }

    public DatosResponseUsuario actualizar(DatosActualizaUsuario datos) {

        validaId(datos.id());
        Usuario usuario = usuarioById(datos.id());
        usuario.actualizarInformacion(datos);

        return new DatosResponseUsuario(usuario);
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
