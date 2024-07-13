package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.curso.CursoService;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosActualizaCurso;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosRegistroCurso;
import com.aluracursos.foro_hub.api.domain.curso.dto.DatosResponseCurso;
import com.aluracursos.foro_hub.api.domain.respuesta.RespuestaService;
import com.aluracursos.foro_hub.api.domain.respuesta.dto.DatosActualizaRespuesta;
import com.aluracursos.foro_hub.api.domain.topico.TopicoService;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosActualizaTopico;
import com.aluracursos.foro_hub.api.domain.topico.dto.DatosCierraTopico;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioRepository;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioService;
import com.aluracursos.foro_hub.api.domain.usuario.dto.*;
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
@RequestMapping("/admin")
@SecurityRequirement(name = "bearer-key")

public class AdminController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    RespuestaService respuestaService;
    @Autowired
    TopicoService topicoService;
    @Autowired
    CursoService cursoService;

    //USUARIOS___________________________________

    //muestra lista de todos usuarios registrados
    @Operation(summary = "Obtener una lista de todos los usuarios registrados")
    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> listaUsuarios(Pageable paginacion){
        var response = usuarioRepository.findByActivoTrue(paginacion).map(DatosListadoUsuarios::new);

        return ResponseEntity.ok(response);
    }

    //muestra todos los datos de cualquier usuario
    @Operation(summary = "Obtener los datos de un usuario registrado")
    @GetMapping("/usuarios/{id}")
    public ResponseEntity consultarUsuario (@PathVariable Long id){
        usuarioService.validaUsuarioIdAndActivo(id);
        var usuario  =  usuarioRepository.getReferenceById(id);
        var response = new DatosResponseUsuario(usuario);

        return ResponseEntity.ok(response);
    }

    //actualiza cualquier usuario
    @Operation(summary = "Actualizar un usuario")
    @PutMapping("/usuarios")
    @Transactional
    public ResponseEntity actualizaUsuario (@RequestBody @Valid DatosActualizaUsuario datos){
        var response = usuarioService.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //actualiza perfil cualquier usuario
    @Operation(summary = "Actualizar perfil de un usuario")
    @PutMapping("/usuarios/perfil")
    @Transactional
    public ResponseEntity actualizaPerfilUsuario (@RequestBody @Valid DatosActualizaPerfilUsuario datos){
        var response = usuarioService.actualizarPerfil(datos);

        return ResponseEntity.ok(response);
    }

    //actualiza la contarseña de cualquier usuario
    @Operation(summary = "Actualizar/Cambiar contraseña de un usuario")
    @PutMapping("/usuarios/{id}")
    @Transactional
    public ResponseEntity actualizaContraseña (@PathVariable Long id, @RequestBody @Valid DatosCambiaContraseñaUsuario datos){
        var response = usuarioService.cambiaContraseña(id,datos);

        return ResponseEntity.ok(response);
    }

    //eliminar cualquier usuario (delete logico)
    @Operation(summary = "Eliminar un usuario")
    @DeleteMapping("/usuarios/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario (@PathVariable Long id){
        usuarioService.delete(id);

        return ResponseEntity.noContent().build();
    }

    //TOPICOS________________________________

    //actualiza informacion cualquier topico
    @Operation(summary = "Actualizar de un topico")
    @PutMapping("/topicos")
    @Transactional
    public ResponseEntity actualizarTopico (@RequestBody @Valid DatosActualizaTopico datos){
        var response = topicoService.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //cerrar cualquier topico
    @Operation(summary = "Cerrar un topico. (No mas respuestas)")
    @PutMapping("/topicos/cerrar")
    @Transactional
    public ResponseEntity cierraTopico (@RequestBody @Valid DatosCierraTopico datos){
        var response = topicoService.cerrar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar cualquier topico (delete logico)
    @Operation(summary = "Eliminar un topico")
    @DeleteMapping("/topicos/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id){
        topicoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    //RESPUESTAS__________________________________


    //actualizar una respuesta
    @Operation(summary = "Actualizar una respuesta")
    @PutMapping("/respuestas")
    @Transactional
    public ResponseEntity actualizarRespuesta (@RequestBody @Valid DatosActualizaRespuesta datos){
        var response = respuestaService.actualizar(datos);

        return ResponseEntity.ok(response);
    }

    //eliminar respuesta (delete logico)
    @Operation(summary = "Eliminar una respuesta")
    @DeleteMapping("/respuestas/{id}")
    @Transactional
    public ResponseEntity eliminarRespuesta (@PathVariable Long id){
        respuestaService.delete(id);

        return ResponseEntity.noContent().build();
    }

    //CURSOS__________________________________


    //registrar un curso
    @Operation(summary = "Registrar un curso")
    @PostMapping("/cursos")
    @Transactional
    public ResponseEntity<DatosResponseCurso> ingresarCurso(@RequestBody @Valid DatosRegistroCurso datos) {
        var response = cursoService.registrar(datos);

        return ResponseEntity.ok(response);
    }

    //actualiza un curso
    @Operation(summary = "Actualizar un curso")
    @PutMapping("/cursos")
    @Transactional
    public ResponseEntity actualizaCurso (@RequestBody @Valid DatosActualizaCurso datos){
        var response = cursoService.actualizar(datos);

        return ResponseEntity.ok(response);
    }
    //eliminar curso (delete logico)
    @Operation(summary = "Borrar un curso")
    @DeleteMapping("(/cursos/{id}")
    @Transactional
    public ResponseEntity eliminarCurso (@PathVariable Long id){
        cursoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}

