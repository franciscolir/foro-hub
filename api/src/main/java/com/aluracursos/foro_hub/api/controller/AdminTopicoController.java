package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.topico.*;
import com.aluracursos.foro_hub.api.domain.topico.dto.*;
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
@RequestMapping("/admin/topicos")
@SecurityRequirement(name = "bearer-key")
public class AdminTopicoController {

    @Autowired
    TopicoRepository repository;
    @Autowired
    TopicoService service;

    //obtener datos de cualquier topico registrado
    @GetMapping("/{id}")
    public ResponseEntity obtenerTopico(@PathVariable Long id){
        service.validaTopicoIdAndActivo(id);
        var topico = service.topicoById(id);
        var response = new DatosResponseTopico(topico);

        return ResponseEntity.ok(response);
    }

    //muestra lista de todos los topicos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listaTopicos(Pageable paginacion){
        var response = repository.findByActivoTrue(paginacion).map(DatosListadoTopicos::new);

        return ResponseEntity.ok(response);
    }

    //actualiza informacion cualquier topico
    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico (@RequestBody @Valid DatosActualizaTopico datos){
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //cerrar cualquier topico
    @PutMapping("/cerrar")
    @Transactional
    public ResponseEntity cierraTopico (@RequestBody @Valid DatosCierraTopico datos){
        var response = service.cerrar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar cualquier topico (delete logico)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}