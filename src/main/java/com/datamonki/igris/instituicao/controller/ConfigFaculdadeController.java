package com.datamonki.igris.instituicao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.datamonki.igris.common.response.ApiResponse;
import com.datamonki.igris.instituicao.model.ConfigFaculdade;
import com.datamonki.igris.instituicao.service.ConfigFaculdadeService;

@RestController
@RequestMapping("/config")
public class ConfigFaculdadeController {

    @Autowired
    private ConfigFaculdadeService configFaculdadeService;


    // Buscar configuração por ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ConfigFaculdade>> getById(@PathVariable Integer id) {

        ConfigFaculdade configFaculdade = configFaculdadeService.getById(id);

        ApiResponse<ConfigFaculdade> response = new ApiResponse.Builder<ConfigFaculdade>(HttpStatus.OK.value(), "Configuração encontrada com sucesso")
                .data(configFaculdade)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    // Listar todas as configurações
    @GetMapping
    public ResponseEntity<ApiResponse<List<ConfigFaculdade>>> getAll() {
        List<ConfigFaculdade> configFaculdades = configFaculdadeService.getAll();

        ApiResponse<List<ConfigFaculdade>> response = new ApiResponse.Builder<List<ConfigFaculdade>>(HttpStatus.OK.value(), "Configurações encontradas com sucesso")
                .data(configFaculdades)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
