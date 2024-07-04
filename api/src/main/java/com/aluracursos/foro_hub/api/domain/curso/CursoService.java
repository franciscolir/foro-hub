package com.aluracursos.foro_hub.api.domain.curso;

import com.aluracursos.foro_hub.api.domain.curso.dto.DatosActualizaCurso;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosRegistroCurso;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosResponseCurso;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CursoService {

    @Autowired
    CursoRepository repository;

    public DatosResponseCurso registrar(DatosRegistroCurso datos) {
        if (repository.existsByIdAndActivoFalse(datos.id())) {
            throw new ValidacionDeIntegridad("este curso ya fue registrado, pero esta inactivo");
        }
        if (repository.existsById(datos.id())) {
            throw new ValidacionDeIntegridad("este curso ya fue registrado");
        }
        var curso = new Curso(datos);
        repository.save(curso);

        return new DatosResponseCurso(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

    public DatosResponseCurso actualizar(DatosActualizaCurso datos) {
        validaId(datos.id());
        Curso curso = cursoById(datos.id());
        curso.actualizarInformacion(datos);

        return new DatosResponseCurso(curso.getId(), curso.getNombre(), curso.getCategoria());
    }


    public void delete(Long id) {
        validaIdAndActivo(id);
        Curso curso = repository.getReferenceById(id);
        curso.inactivarCurso();


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

    public Curso cursoById (Long id){
        Curso curso = repository.getReferenceById(id);

        return curso;
    }

}
