package com.aluracursos.foro_hub.api.domain.perfil;


import com.aluracursos.foro_hub.api.domain.perfil.dto.DatosActualizaPerfil;
import com.aluracursos.foro_hub.api.domain.perfil.dto.DatosRegistroPerfil;
import com.aluracursos.foro_hub.api.domain.perfil.dto.DatosResponsePerfil;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PerfilService {

    @Autowired
    PerfilRepository repository;

    public DatosResponsePerfil registrar(DatosRegistroPerfil datos) {

        if (repository.existsById(datos.id())) {
            throw new ValidacionDeIntegridad("este id de perfil ya fue registrado");
        }
        var perfil = new Perfil(datos);
        repository.save(perfil);

        return new DatosResponsePerfil(perfil);
    }

    public DatosResponsePerfil actualizar(DatosActualizaPerfil datos) {

        validaId(datos.id());
        Perfil perfil = perfilById(datos.id());
        perfil.actualizarInformacion(datos);

        return new DatosResponsePerfil(perfil);
    }


    public void delete(Long id) {
        validaIdAndActivo(id);
        Perfil perfil = repository.getReferenceById(id);
        perfil.inactivarPerfil();
    }

    public Boolean validaIdAndActivo(Long id) {
        if (!repository.existsByIdAndActivoTrue(id)) {
            throw new ValidacionDeIntegridad("este id de perfil no existe");
        }
        return true;
    }

    public void validaId (Long id){
        if (!repository.findById(id).isPresent()) {
            throw new ValidacionDeIntegridad("este id de perfil no es valido");
        }
    }

    public Perfil perfilById (Long id){
        Perfil perfil = repository.getReferenceById(id);

        return perfil;
    }

}
