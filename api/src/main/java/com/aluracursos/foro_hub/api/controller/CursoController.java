package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.curso.*;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosListadoCursos;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosResponseCurso;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/curso")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    CursoRepository repository;
    @Autowired
    CursoService service;

    //muestra lista de cursos
    @Operation(summary = "Obtener una lista de los cursos")
    @GetMapping
    public ResponseEntity<Page<DatosListadoCursos>> listaCursos(Pageable paginacion){
        var response = repository.findByActivoTrue(paginacion).map(DatosListadoCursos::new);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos de 1 curso
    @Operation(summary = "Obtener datos de un curso")
    @GetMapping("/{id}")
    public ResponseEntity consultarCurso (@PathVariable Long id){
        service.validaIdAndActivo(id);
        var curso  = service.cursoById(id);
        var response = new DatosResponseCurso(curso);

        return ResponseEntity.ok(response);
    }
}