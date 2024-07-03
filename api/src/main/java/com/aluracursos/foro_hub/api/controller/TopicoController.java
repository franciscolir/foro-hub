package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.domain.topico.*;
import com.aluracursos.foro_hub.api.domain.usuario.DatosListadoUsuarios;
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
@RequestMapping("/topicos")
//@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    TopicoRepository repository;
    @Autowired
    TopicoService service;

    @PostMapping
    @Transactional

    public ResponseEntity ingresarTopico(@RequestBody @Valid DatosRegistraTopico datos) {
        var response =service.ingresarTopico(datos);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity obtenerTopico(@PathVariable Long id){
        service.validaIdAndActivo(id);
        var topico = repository.getReferenceById(id);
        var response = new DatosResponseTopico(topico);
        return ResponseEntity.ok(response);
    }
    //muestra lista de topicos
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopicos>> listaTopicos(Pageable paginacion){
       var response = repository.findByActivoTrue(paginacion).map(DatosListadoTopicos::new);
        return ResponseEntity.ok(response);
    }


    //eliminar topico (delete logico)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
