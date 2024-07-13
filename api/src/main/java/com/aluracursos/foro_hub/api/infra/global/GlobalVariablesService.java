package com.aluracursos.foro_hub.api.infra.global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GlobalVariablesService {

    @Autowired
    GlobalVariables globalVariables;

    public void setGlobalIdUsuario(Long id) {
        globalVariables.setUserTokenId(id);
            }
}
