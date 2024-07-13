package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.topico.*;
import com.aluracursos.foro_hub.api.domain.topico.dto.*;
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
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    TopicoRepository repository;
    @Autowired
    TopicoService service;
    @Autowired
    GlobalVariables globalVariables;

    //registra un nuevo topico
    @Operation(summary = "Registrar un topico")
    @PostMapping
    @Transactional
    public ResponseEntity ingresarTopico(@RequestBody @Valid DatosRegistraTopico datos) {
        var response = service.ingresarTopico(datos);

        return ResponseEntity.ok(response);
    }

    //muestra lista de topicos ingredasos por el usuario
    @Operation(summary = "Obtener una lista de todos los topicos ingresados por usuario autenticado")
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listaTopicosUsuario(Pageable paginacion){
        var usuarioId = globalVariables.getUserTokenId();
        var response = repository.findByUsuarioIdActivoTrue(usuarioId,paginacion).map(DatosListadoTopicos::new);

        return ResponseEntity.ok(response);
    }

    //muestra lista de todos los topicos}
    @Operation(summary = "Obtener una lista de todos los topico registrado")
    @GetMapping("/all")
    public ResponseEntity<Page<DatosListadoTopicos>> listaTopicos(Pageable paginacion){
        var response = repository.findByActivoTrue(paginacion).map(DatosListadoTopicos::new);

        return ResponseEntity.ok(response);
    }

    //obtener datos de un topico
    @Operation(summary = "Obtener los datos de un topico")
    @GetMapping("/{id}")
    public ResponseEntity obtenerTopico(@PathVariable Long id){
        service.validaTopicoIdAndActivo(id);
        var topico = service.topicoById(id);
        var response = new DatosResponseTopico(topico);

        return ResponseEntity.ok(response);
    }

    //actualiza informacion topico y estado
    @Operation(summary = "Actualizar un topico ingresado por usuario autenticado")
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico (@RequestBody @Valid DatosActualizaTopico datos){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, datos.id());
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //cierra el topico
    @Operation(summary = "Cerrar un topico ingresado por usuario autenticado")
    @PutMapping("/cerrar")
    @Transactional
    public ResponseEntity cierraTopico (@RequestBody @Valid DatosCierraTopico datos){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, datos.id());
        var response = service.cerrar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar topico (delete logico)
    @Operation(summary = "Eliminar un topico ingresado por usuario autenticado")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
