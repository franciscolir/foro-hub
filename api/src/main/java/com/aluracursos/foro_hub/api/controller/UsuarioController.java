package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.domain.usuario.*;
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
@RequestMapping("/usuario")
//@SecurityRequirement(name = "bearer-key")

public class UsuarioController {
    @Autowired
    UsuarioService service;
    @Autowired
    UsuarioRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<DatosResponseUsuario> ingresarTopico(@RequestBody @Valid DatosRegistroUsuario datos) {

        var response = service.registrar(datos);
        return ResponseEntity.ok(response);
    }
    //Get para obtener todos los usuarios con paginacion


    //muestra lista de usuarios
   @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> listaMedico(Pageable paginacion){
    var response = repository.findByActivoTrue(paginacion).map(DatosListadoUsuarios::new);
        return ResponseEntity.ok(response);
    }



    //muestra todos los datos de 1 usuario
    @GetMapping("/{id}")
    public ResponseEntity consultarUsuario (@PathVariable Long id){
    service.validaIdAndActivo(id);
    var usuario  =  repository.getReferenceById(id);
    var response = new DatosResponseUsuario(usuario.getId(), usuario.getNombre(),usuario.getPerfil());
        return ResponseEntity.ok(response);
    }

    //actualiza un usuario
    @PutMapping
    @Transactional
    public ResponseEntity actualizaUsuario (@RequestBody @Valid DatosActualizaUsuario datos){

        var response = service.actualizar(datos);
        return ResponseEntity.ok(response);
    }
    //actualiza un contarseña de usuario
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizaContraseña (@PathVariable Long id, @RequestBody @Valid DatosCambiaContraseñaUsuario datos){

        var response = service.cambiaContraseña(id,datos);
        return ResponseEntity.ok(response);
    }

    //eliminar usuario (delete logico)
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
