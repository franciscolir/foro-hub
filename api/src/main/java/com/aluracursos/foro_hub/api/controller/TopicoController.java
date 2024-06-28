package com.aluracursos.foro_hub.api.controller;


import com.aluracursos.foro_hub.api.domain.topico.Topico;
import com.aluracursos.foro_hub.api.domain.topico.TopicoRepository;
import com.aluracursos.foro_hub.api.domain.topico.DatosRegistraTopico;
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
@RequestMapping("/topicos")
//@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    TopicoRepository repository;

    @PostMapping
    @Transactional

    public ResponseEntity ingresarTopico(@RequestBody @Valid DatosRegistraTopico datos) {
        Topico topico = new Topico(datos);
        var response = repository.save(topico);
        return ResponseEntity.ok(response);

    }
}
