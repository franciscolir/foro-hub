package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.DatosResponseUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.Usuario;
import com.aluracursos.foro_hub.api.domain.usuario.UsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

            var response = service.validar(datos);
            return ResponseEntity.ok(response);
        }
        //muestra todos los datos de 1 usuario
         @GetMapping("/{id}")
         public ResponseEntity consultarUsuario (@PathVariable Long id){

            var usuario  =  repository.findById(id);
             return ResponseEntity.ok(new DatosResponseUsuario(usuario.get().getId(), usuario.get().getNombre(),usuario.get().getPerfil()));
    }

}
