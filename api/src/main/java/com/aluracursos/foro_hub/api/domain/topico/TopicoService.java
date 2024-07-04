package com.aluracursos.foro_hub.api.domain.topico;


import com.aluracursos.foro_hub.api.domain.curso.CursoRepository;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosActualizaTopico;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosRegistraTopico;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosResponseTopico;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioRepository;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TopicoService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    TopicoRepository topicoRepository;

    public DatosResponseTopico ingresarTopico (DatosRegistraTopico datos){

        if (!usuarioRepository.existsByIdAndActivoTrue(datos.usuarioId())) {
            throw new ValidacionDeIntegridad("este id para el usuario no fue encontrado");
        }

        if(!cursoRepository.findById(datos.cursoId()).isPresent()){
            throw new ValidacionDeIntegridad("este id para el curso no fue encontrado");
        }

        var usuario = usuarioRepository.findById(datos.usuarioId()).get();
        var curso = cursoRepository.findById(datos.cursoId()).get();
        var fecha = LocalDateTime.now();

        var topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                fecha,
                Estado.CREADO,
                usuario,
                curso,
                true,
                null);

        topicoRepository.save(topico);

        return new DatosResponseTopico(topico);

    }
    public DatosResponseTopico actualizar(DatosActualizaTopico datos) {
        validaIdAndActivo(datos.id());
        Topico topico = topicoRepository.getReferenceById(datos.id());
        topico.actualizarInformacion(datos);
        
        return new DatosResponseTopico(topico);
    }

    public void delete(Long id) {
        validaIdAndActivo(id);
        Topico topico= topicoRepository.getReferenceById(id);
        topico.inactivarUsuario();

    }

    public Boolean validaIdAndActivo(Long id) {
        if (!topicoRepository.existsByIdAndActivoTrue(id)) {

            throw new ValidacionDeIntegridad("este id de usuario no existe");
        }
        return true;
    }



}
