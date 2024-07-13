package com.aluracursos.foro_hub.api.domain.usuario;

import com.aluracursos.foro_hub.api.domain.perfil.PerfilRepository;
import com.aluracursos.foro_hub.api.domain.usuario.dto.*;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository repository;
    @Autowired
    PerfilRepository perfilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //Registra un nuevo usuario
    public DatosResponseUsuario registrar(DatosRegistroUsuario datos) {
        validaUsuarioEmailAndActivoFalse(datos.correoElectronico());
        validaUsuarioEmail(datos.correoElectronico());
        var perfil = perfilRepository.findById(2L).get();
        var usuario = new Usuario(null, datos.nombre(), datos.correoElectronico(), datos.contraseña(), perfil, true);
        repository.save(usuario);

        return new DatosResponseUsuario(usuario);
    }

    //Actualiza un usuario registrado por id
    public DatosResponseUsuario actualizar(DatosActualizaUsuario datos) {
        validaUsuarioIdAndActivo(datos.id());
        var usuario = usuarioById(datos.id());
        usuario.actualizarInformacion(datos.id(),datos.nombre());

        return new DatosResponseUsuario(usuario);
    }

    //Actualiza perfil de usuario registrado por id
    public DatosResponseUsuario actualizarPerfil(DatosActualizaPerfilUsuario datos) {
        validaUsuarioIdAndActivo(datos.id());
        var usuario = usuarioById(datos.id());
        var perfil = perfilRepository.getReferenceById(datos.perfilId());
        usuario.actualizarPerfil(datos.id(),perfil);

        return new DatosResponseUsuario(usuario);
    }

    //Cambia contraseña de usuario
    public String cambiaContraseña(Long id, DatosCambiaContraseñaUsuario datos) {

        validaUsuarioIdAndActivo(id);
        var claveIngresada = datos.actualContraseña();
        var usuario = repository.findById(id);
        var claveRegistrada = usuario.get().getContraseña();

        if (passwordEncoder.matches(claveIngresada,claveRegistrada)) {
            var claveNueva = passwordEncoder.encode(datos.nuevaContraseña());
            usuarioById(id).actualizarContraseña(claveNueva);
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

        return repository.getReferenceById(id);
    }

    //compara id del token con el id del usuario indicado
    public void comparaId (Long tokenId, Long usuarioId ){
        if(!tokenId.equals(usuarioId)){
            throw new ValidacionDeIntegridad("id no corresponde a usuario autenticado");
        }
    }

}
