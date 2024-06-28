package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.foro_hub.api.domain.usuario.Usuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@ResponseBody
@RequestMapping("/usuario")
//@SecurityRequirement(name = "bearer-key")

public class UsuarioController {


        @Autowired
        UsuarioRepository repository;

        @PostMapping
        @Transactional

        public ResponseEntity ingresarTopico(@RequestBody @Valid DatosRegistroUsuario datos) {

            Usuario usuario = new Usuario(datos);
            var response = repository.save(usuario);
            return ResponseEntity.ok("Usuario "+datos.nombre()+" registrado");

        }

}
