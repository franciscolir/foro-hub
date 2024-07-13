package com.aluracursos.foro_hub.api.controller;

import com.aluracursos.foro_hub.api.domain.usuario.Usuario;
import com.aluracursos.foro_hub.api.domain.usuario.dto.DatosAutenticacionUsuario;
import com.aluracursos.foro_hub.api.infra.global.GlobalVariablesService;
import com.aluracursos.foro_hub.api.infra.security.DatosTokenJWT;
import com.aluracursos.foro_hub.api.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Tag(name = "Foro Hub", description = "API para foro. incluir datos para test")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    GlobalVariablesService globalVariablesService;

    @Operation(summary = "Autenticar Usuario")
    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody @Valid DatosAutenticacionUsuario usuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(usuario.correoElectronico(),usuario.contraseña());
        var authentication = manager.authenticate(authToken);
        var tokenJWT = tokenService.generarToken((Usuario) authentication.getPrincipal());
        var extractName = tokenService.extractUsername(tokenJWT);
        var extraerId = tokenService.extraeIdDelToken(extractName);
        globalVariablesService.setGlobalIdUsuario(extraerId);

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
