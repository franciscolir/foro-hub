package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.respuesta.*;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosActualizaRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosListadoRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosRegistroRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosResponseRespuesta;
import com.aluracursos.foro_hub.api.domain.user.UserNameRepository;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioRepository;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")

public class RespuestaController {

    @Autowired
    RespuestaService service;
    @Autowired
    RespuestaRepository repository;
    @Autowired
    UserNameRepository userNameRepository;


    //registra una nueva respuesta

    @PostMapping
    @Transactional
    public ResponseEntity<DatosResponseRespuesta> ingresarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos) {
        var response = service.registrar(datos);

        return ResponseEntity.ok(response);
    }

    //muestra lista de respuestas del usuario

    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listadoDeRespuestas (Pageable paginacion){
        var id = userNameRepository.getReferenceById(1L);
        var usuarioId = id.getTokenId();
        var response = repository.findByAutorRespuestaAndActivoTrue(usuarioId,paginacion).map(DatosListadoRespuesta::new);
        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos de 1 respuesta

    @GetMapping("/{id}")
    public ResponseEntity obtenerRespuesta (@PathVariable Long id){
        service.comparaId(1L,id);
        service.validaRespuestaIdAndActivo(id);
        var respuesta  =  service.obtenerRespuestaById(id);
        var response = new DatosResponseRespuesta(respuesta);

        return ResponseEntity.ok(response);
    }

    //actualizar una respuesta

    @PutMapping
    @Transactional
    public ResponseEntity actualizarRespuesta (@RequestBody @Valid DatosActualizaRespuesta datos){
        service.comparaId(1L, datos.id());
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar respuesta (delete logico)

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta (@PathVariable Long id){
        service.comparaId(1L, id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
