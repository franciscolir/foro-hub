package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.usuario.*;
import com.aluracursos.foro_hub.api.domain.usuario.dto.*;
import com.aluracursos.foro_hub.api.infra.global.GlobalVariables;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@ResponseBody
@RequestMapping("/usuario")
@SecurityRequirement(name = "bearer-key")

public class UsuarioController {

    @Autowired
    UsuarioService service;
    @Autowired
    UsuarioRepository repository;
    @Autowired
    GlobalVariables globalVariables;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //ingresar un usuario
    @Operation(summary = "Registrar un usuario")
    @PostMapping
    @Transactional
    public ResponseEntity<DatosResponseUsuario> ingresarUsuario(@RequestBody @Valid DatosRegistroUsuario datos) {
        var response = service.registrar(datos);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos del usuario autenticado
    @Operation(summary = "Obtener los datos del usuario autenticado")
    @GetMapping("/my")
        public ResponseEntity consultarUsuario (){
        var usuarioId = globalVariables.getUserTokenId();
        service.validaUsuarioIdAndActivo(usuarioId);
        var usuario  =  repository.getReferenceById(usuarioId);
        var response = new DatosResponseUsuario(usuario);

        return ResponseEntity.ok(response);
    }

    //actualiza el usuario autenticado
    @Operation(summary = "Actualizar usuario autenticado")
    @PutMapping
    @Transactional
    public ResponseEntity actualizaUsuario (@RequestBody @Valid DatosActualizaUsuario datos){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, datos.id());
        var response = service.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //actualiza un contarseña de usuario autenticado
    @Operation(summary = "Actualizar/Cambiar contraseña usuario autenticado")
    @PutMapping("/my")
    @Transactional
    public ResponseEntity actualizaContraseña (@RequestBody @Valid DatosCambiaContraseñaUsuario datos){
        var usuarioId = globalVariables.getUserTokenId();
        var response = service.cambiaContraseña(usuarioId,datos);

        return ResponseEntity.ok(response);
    }

    //eliminar usuario autenticado (delete logico)
    @Operation(summary = "Eliminar usuario autenticado")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario (@PathVariable Long id){
        var usuarioId = globalVariables.getUserTokenId();
        service.comparaId(usuarioId, id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
