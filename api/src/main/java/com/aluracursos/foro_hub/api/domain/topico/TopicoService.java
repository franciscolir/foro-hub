package com.aluracursos.foro_hub.api.domain.topico;


import com.aluracursos.foro_hub.api.domain.curso.CursoRepository;
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

            //paciente
            //if(!usuarioRepository.findById(datos.usuarioId()).isPresent()){
                if (!usuarioRepository.existsByIdAndActivoTrue(datos.usuarioId())) {
                System.out.println(datos.usuarioId()+"datos usuario id en service dentro del if");
                throw new ValidacionDeIntegridad("este id para el usuario no fue encontrado");
            }
            //medico
            if(!cursoRepository.findById(datos.cursoId()).isPresent()){
                System.out.println(datos.cursoId()+"datos curso id en service dentro del if");
                throw new ValidacionDeIntegridad("este id para el curso no fue encontrado");
            }

            //validadores.forEach(v-> v.validar(datos));

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
                    null);
        System.out.println(topico+"topico despues de instanciar objeto topico antes de retornar hacia controller");
            topicoRepository.save(topico);

            return new DatosResponseTopico(topico);

    }


}
