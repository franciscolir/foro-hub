package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.usuario.UsuarioRepository;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioService;
import com.aluracursos.foro_hub.api.domain.usuario.dto.*;
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
@RequestMapping("/admin/usuarios")
@SecurityRequirement(name = "bearer-key")
//cambiar nombre a clase, agregar actualizar perfil
public class AdminUsuarioController {

    @Autowired
    UsuarioService service;
    @Autowired
    UsuarioRepository repository;

    //muestra lista de todos usuarios registrados
    @GetMapping()
    public ResponseEntity<Page<DatosListadoUsuarios>> listaUsuarios(Pageable paginacion){
        var response = repository.findByActivoTrue(paginacion).map(DatosListadoUsuarios::new);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos de cualquier usuario
    @GetMapping("/{id}")
    public ResponseEntity consultarUsuario (@PathVariable Long id){
        service.validaUsuarioIdAndActivo(id);
        var usuario  =  repository.getReferenceById(id);
        var response = new DatosResponseUsuario(usuario);

        return ResponseEntity.ok(response);
    }

    //actualiza cualquier usuario
    @PutMapping
    @Transactional
    public ResponseEntity actualizaUsuario (@RequestBody @Valid DatosActualizaUsuario datos){
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //actualiza perfil cualquier usuario
    @PutMapping
    @Transactional
    public ResponseEntity actualizaPerfilUsuario (@RequestBody @Valid DatosActualizaPerfilUsuario datos){
        var response = service.actualizarPerfil(datos);

        return ResponseEntity.ok(response);
    }


    //actualiza la contarseña de cualquier usuario
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizaContraseña (@PathVariable Long id, @RequestBody @Valid DatosCambiaContraseñaUsuario datos){
        var response = service.cambiaContraseña(id,datos);

        return ResponseEntity.ok(response);
    }

    //eliminar cualquier usuario (delete logico)

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario (@PathVariable Long id){
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}

