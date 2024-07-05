package com.aluracursos.foro_hub.api.domain.respuesta;


import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosActualizaRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosRegistroRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosResponseRespuesta;
import com.aluracursos.foro_hub.api.domain.topico.TopicoRepository;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioRepository;
import com.aluracursos.foro_hub.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class RespuestaService {
    @Autowired
    RespuestaRepository respuestaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TopicoRepository topicoRepository;


    public DatosResponseRespuesta registrar(DatosRegistroRespuesta datos) {

        validaTopicoIdAndActivo(datos.topicoId());
        validaUsuarioIdAndActivo(datos.autorRespuestaId());

        var topico = topicoRepository.findById(datos.topicoId()).get();
        var usuario = usuarioRepository.findById(datos.autorRespuestaId()).get();
        var fechaCreacion = LocalDateTime.now();
        var respuesta = new Respuesta(
                null,
                datos.mensaje(),
                topico,
                fechaCreacion,
                usuario,
                datos.solucion(),
                true);
        respuestaRepository.save(respuesta);

        return new DatosResponseRespuesta(respuesta);
    }

    public DatosResponseRespuesta actualizar(DatosActualizaRespuesta datos) {

        validaUsuarioIdAndActivo(datos.id());
        var respuesta = obtenerRespuestaById(datos.id());
        respuesta.actualizarInformacion(datos);

        return new DatosResponseRespuesta(respuesta);
    }

    public void delete(Long id) {
        validaUsuarioIdAndActivo(id);
        var respuesta = obtenerRespuestaById(id);
        respuesta.inactivarRespuesta();
    }

    public Boolean validaUsuarioIdAndActivo(Long id) {
        if (!usuarioRepository.existsByIdAndActivoTrue(id)) {

            throw new ValidacionDeIntegridad("este id de usuario no existe");
        }
        return true;

    }
    public Boolean validaTopicoIdAndActivo(Long id) {
        if (!topicoRepository.existsByIdAndActivoTrue(id)) {

            throw new ValidacionDeIntegridad("este id de topico no existe");
        }
        return true;
    }

    public Boolean validaRespuestaIdAndActivo(Long id) {
        if (!respuestaRepository.existsByIdAndActivoTrue(id)) {

            throw new ValidacionDeIntegridad("este id de respuesta no existe");
        }
        return true;
    }

    public Respuesta obtenerRespuestaById (Long id){
        Respuesta respuesta = respuestaRepository.getReferenceById(id);

        return respuesta;
    }
}
