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

    //Registra una nueva respuesta
    public DatosResponseRespuesta registrar(DatosRegistroRespuesta datos) {
        validaTopicoIdAndActivo(datos.topicoId());
        validaUsuarioIdAndActivo(datos.autorRespuestaId());
        var topico = topicoRepository.findById(datos.topicoId()).get();
        var usuario = usuarioRepository.findById(datos.autorRespuestaId()).get();
        var fechaCreacion = LocalDateTime.now();
        var respuesta = new Respuesta( null, datos.mensaje(), topico, fechaCreacion, usuario, datos.solucion(), true);
        respuestaRepository.save(respuesta);

        return new DatosResponseRespuesta(respuesta);
    }

    //Actualiza un respuesta segun id
    public DatosResponseRespuesta actualizar(DatosActualizaRespuesta datos) {
        validaTopicoIdAndActivo(datos.id());
        var respuesta = obtenerRespuestaById(datos.id());
        respuesta.actualizarInformacion(datos);

        return new DatosResponseRespuesta(respuesta);
    }

    //Inactiva una respúesta
    public void delete(Long id) {
        validaTopicoIdAndActivo(id);
        var respuesta = obtenerRespuestaById(id);
        respuesta.inactivarRespuesta();
    }

    public void validaUsuarioIdAndActivo(Long id) {
        if (!usuarioRepository.existsByIdAndActivoTrue(id)) {
            throw new ValidacionDeIntegridad("este id de usuario no existe");
        }

    }
    public void validaTopicoIdAndActivo(Long id) {
        if (!topicoRepository.existsByIdAndActivoTrue(id)) {
            throw new ValidacionDeIntegridad("este id de topico no existe");
        }
    }

    public void validaRespuestaIdAndActivo(Long id) {
        if (!respuestaRepository.existsByIdAndActivoTrue(id)) {
            throw new ValidacionDeIntegridad("este id de respuesta no existe");
        }
    }

    public Respuesta obtenerRespuestaById (Long id){

        return respuestaRepository.getReferenceById(id);
    }
    //compara id del token con el id del usuario indicado
    public void comparaId (Long tokenId, Long id ){
        var respuesta = respuestaRepository.getReferenceById(id);
        var usuarioId = respuesta.getAutorRespuesta().getId();

        if(!tokenId.equals(usuarioId)){
            throw new ValidacionDeIntegridad("id no corresponde a usuario autenticado");
        }
    }
}
