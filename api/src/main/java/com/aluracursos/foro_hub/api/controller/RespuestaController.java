package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.respuesta.*;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosActualizaRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosListadoRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosRegistroRespuesta;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosResponseRespuesta;
import com.aluracursos.foro_hub.api.infra.global.GlobalVariables;
import io.swagger.v3.oas.annotations.Operation;
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
    GlobalVariables globalVariables;


    //registra una nueva respuesta
    @Operation(summary = "Registrar una respuesta")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosResponseRespuesta> ingresarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos) {
        var response = service.registrar(datos);

        return ResponseEntity.ok(response);
    }

    //muestra lista de respuestas del usuario autenticado
    @Operation(summary = "Obtener una lista de respuestas ingresada por usuario autenticado")
    @GetMapping
    public ResponseEntity<Page<DatosListadoRespuesta>> listadoDeRespuestasUsuario (Pageable paginacion){
        var usuarioId = globalVariables.getUserTokenId();
        System.out.println(usuarioId+ "ESTA ES EL GET DE VARIABLE GLOBAL");
        var response = repository.findByAutorRespuestaAndActivoTrue(usuarioId,paginacion).map(DatosListadoRespuesta::new);

        return ResponseEntity.ok(response);
    }

    //muestra lista de respuestas
    @Operation(summary = "Obtener una lista de respuestas de todos los usuarios registrados")
    @GetMapping("/all")
    public ResponseEntity<Page<DatosListadoRespuesta>> listadoDeRespuestas (Pageable paginacion){
        var response = repository.findByActivoTrue(paginacion).map(DatosListadoRespuesta::new);
        System.out.println(response);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos de 1 respuesta
    @Operation(summary = "Obtener los datos de una respuesta")
    @GetMapping("/{id}")
    public ResponseEntity obtenerRespuesta (@PathVariable Long id){
        service.validaRespuestaIdAndActivo(id);
        var respuesta  =  service.obtenerRespuestaById(id);
        var response = new DatosResponseRespuesta(respuesta);

        return ResponseEntity.ok(response);
    }

    //actualizar una respuesta del usuario autenticado
    @Operation(summary = "Actualizar una respuesta ingresada por usuario autenticado")
    @PutMapping
    @Transactional
    public ResponseEntity actualizarRespuestaUsuario (@RequestBody @Valid DatosActualizaRespuesta datos){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, datos.id());
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar una respuesta del usuario autenticado (delete logico)
    @Operation(summary = "Eliminar una respuesta ingresada por usuario autenticado")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarRespuestaDeUsuario (@PathVariable Long id){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
